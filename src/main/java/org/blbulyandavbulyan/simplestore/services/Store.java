package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.entites.Product;
import org.blbulyandavbulyan.simplestore.services.exceptions.ConsumerNotFoundException;
import org.blbulyandavbulyan.simplestore.services.exceptions.ProductNotFoundException;
import org.blbulyandavbulyan.simplestore.services.interfaces.IConsumersRepository;
import org.blbulyandavbulyan.simplestore.services.interfaces.IProductsRepository;
import org.blbulyandavbulyan.simplestore.services.interfaces.IStore;

import java.util.Collection;
import java.util.Optional;

import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runForEntityManager;
import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runInTransaction;

/**
 * Предоставляет базовую имплементацию интерфейса IStore.
 * Использует EntityManagerFactory для сохранения сущностей в базу
 */
public class Store implements IStore {
    /**
     * Фабрика, с помощью которой будут создаваться EntityManger для управления сущностями в базе
     */
    private final EntityManagerFactory emf;
    /**
     * Ссылка на репозиторий для продуктов
     */
    private final IProductsRepository iProductsRepository;
    /**
     * Ссылка не репозиторий для покупателей
     */
    private final IConsumersRepository iConsumersRepository;

    /**
     * Создаёт экземпляр магазина
     *
     * @param emf EntityManagerFactory, которая будет использоваться магазином
     * @throws IllegalArgumentException если emf null или закрыта
     */
    public Store(EntityManagerFactory emf) {
        if (emf == null || !emf.isOpen())
            throw new IllegalArgumentException("Entity manager factory is null or closed!");
        this.emf = emf;
        iProductsRepository = new ProductsRepository(emf);
        iConsumersRepository = new ConsumersRepository(emf);
    }

    @Override
    public Collection<BoughtProduct> getBoughtProductsByConsumerName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null!");
        return runForEntityManager(emf, em -> {
            if (iConsumersRepository.existsByName(name)) {
                var selectBoughtProductsQuery = em.createQuery("SELECT c.boughtProducts FROM Consumer c WHERE c.name = :name", BoughtProduct.class);
                selectBoughtProductsQuery.setParameter("name", name);
                return selectBoughtProductsQuery.getResultList();
            } else throw new ConsumerNotFoundException("покупатель с именем " + name + " не найден", name);
        });
    }

    @Override
    public Collection<Consumer> getConsumersByProductTitle(String title) {
        if (title == null) throw new IllegalArgumentException("title is null!");
        return runForEntityManager(emf, em -> {
            if (iProductsRepository.existsByTitle(title)) {
                var selectConsumersByProductTitleQuery = em.createQuery("SELECT bp.consumer FROM BoughtProduct bp WHERE bp.product.title = :title", Consumer.class);
                selectConsumersByProductTitleQuery.setParameter("title", title);
                return selectConsumersByProductTitleQuery.getResultList();
            } else throw new ProductNotFoundException("продукт с названием " + title + " не найден!", title);
        });
    }


    @Override
    public BoughtProduct buy(Long consumerId, Long productId) {
        if (consumerId == null) throw new IllegalArgumentException("consumerId is null!");
        if (productId == null) throw new IllegalArgumentException("productId is null!");
        return runInTransaction(emf, (em) -> {
            Consumer consumer = Optional.ofNullable(em.find(Consumer.class, consumerId))
                    .orElseThrow(() -> new ConsumerNotFoundException("consumer with id" + consumerId + " not found!", consumerId));
            Product product = Optional.ofNullable(em.find(Product.class, productId))
                    .orElseThrow(() -> new ProductNotFoundException("product with id " + productId + " not found!", productId));
            //на данном этапе у нас точно существует и продукт и покупатель
            BoughtProduct boughtProduct = new BoughtProduct(product, consumer, product.getPrice());//создаём запись о купленном продукте
            em.persist(boughtProduct);//сохраняем запись о покупке в базу
            return boughtProduct;
        });
    }

    @Override
    public IConsumersRepository getConsumerRepository() {
        return iConsumersRepository;
    }

    @Override
    public IProductsRepository getProductRepository() {
        return iProductsRepository;
    }
}
