package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
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
public class Store implements IStore, IConsumersRepository, IProductsRepository {
    /**
     * Фабрика, с помощью которой будут создаваться EntityManger для управления сущностями в базе
     */
    private final EntityManagerFactory emf;

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
    }

    @Override
    public Collection<BoughtProduct> getBoughtProductsByConsumerName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null!");
        return runForEntityManager(emf, em -> {
            var checkExistConsumerQuery = em.createQuery("SELECT COUNT(c) FROM Consumer c WHERE c.name = :name", Long.class);
            checkExistConsumerQuery.setParameter("name", name);
            if (checkExistConsumerQuery.getSingleResult() > 0) {
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
            var checkExistConsumerQuery = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.title = :title", Long.class);
            checkExistConsumerQuery.setParameter("title", title);
            if (checkExistConsumerQuery.getSingleResult() > 0) {
                var selectConsumersByProductTitleQuery = em.createQuery("SELECT bp.consumer FROM BoughtProduct bp WHERE bp.product.title = :title", Consumer.class);
                selectConsumersByProductTitleQuery.setParameter("title", title);
                return selectConsumersByProductTitleQuery.getResultList();
            } else throw new ProductNotFoundException("продукт с названием " + title + " не найден!", title);
        });
    }

    @Override
    public boolean deleteConsumer(String name) {
        if (name == null) throw new IllegalArgumentException("name is null!");
        return runInTransaction(emf, (em) -> {
            Query deleteConsumerQuery = em.createQuery("DELETE FROM Consumer c WHERE c.name = :name");
            int result = deleteConsumerQuery.setParameter("name", name).executeUpdate();
            return result > 0;//если result > 0 значит были затронуты записи запросом
        });
    }

    @Override
    public boolean deleteProduct(String title) {
        if (title == null) throw new IllegalArgumentException("title is null!");
        return runInTransaction(emf, (em) -> {
            Query deleteProductQuery = em.createQuery("DELETE FROM Product p WHERE p.title = :title");
            int result = deleteProductQuery.setParameter("title", title).executeUpdate();
            return result > 0;//если result > 0 значит были затронуты записи запросом
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
    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("product is null!");
        runInTransaction(emf, em -> {
            em.persist(product);
            //Поскольку у нас тип у лямды Function, мы обязательно должны что-то вернуть,
            // возвращаем null, т.к. больше нечего вернуть
            return null;
        });
    }

    @Override
    public void addConsumer(Consumer consumer) {
        if (consumer == null) throw new IllegalArgumentException("consumer is null!");
        runInTransaction(emf, em -> {
            em.persist(consumer);
            return null;
        });
    }
}
