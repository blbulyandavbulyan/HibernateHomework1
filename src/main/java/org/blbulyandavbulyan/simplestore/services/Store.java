package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.entites.Product;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Предоставляет базовую имплементацию интерфейса IStore
 */
public class Store implements IStore{
    private EntityManagerFactory emf;

    /**
     * Создаёт экземпляр магазина
     * @param emf EntityManagerFactory, которая будет использоваться магазином
     */
    public Store(EntityManagerFactory emf){
        if(emf == null || !emf.isOpen())throw new IllegalArgumentException("Entity manager factory is null or closed!");
        this.emf = emf;
    }
    @Override
    public Collection<BoughtProduct> getBoughtProductsByConsumerName(String name) {
        return runForEntityManager(em->{
            var selectBoughtProductsQuery = em.createQuery("SELECT c.boughtProducts FROM Consumer c WHERE c.name = :name", BoughtProduct.class);
            selectBoughtProductsQuery.setParameter("name", name);
            return selectBoughtProductsQuery.getResultList();
        });
    }

    @Override
    public Collection<Consumer> getConsumersByProductTitle(String title) {
        return runForEntityManager(em->{
            var selectConsumersByProductTitleQuery = em.createQuery("SELECT bp.consumer FROM BoughtProduct bp WHERE bp.product.title = :title", Consumer.class);
            selectConsumersByProductTitleQuery.setParameter("title", title);
            return selectConsumersByProductTitleQuery.getResultList();
        });
    }

    @Override
    public boolean deleteConsumer(String name) {
        return runInTransaction((em)->{
            Query deleteConsumerQuery = em.createQuery("DELETE FROM Consumer c WHERE c.name = :name");
            int result = deleteConsumerQuery.setParameter("name", name).executeUpdate();
            return result > 0;//если result > 0 значит были затронуты записи запросом
        });
    }

    @Override
    public boolean deleteProduct(String title) {
        return runInTransaction((em)->{
            Query deleteProductQuery = em.createQuery("DELETE FROM Product p WHERE p.title = :title");
            int result = deleteProductQuery.setParameter("title", title).executeUpdate();
            return result > 0;//если result > 0 значит были затронуты записи запросом
        });
    }

    @Override
    public BoughtProduct buy(Long consumerId, Long productId) {
        return runInTransaction((em)->{
           Consumer consumer = Optional.ofNullable(em.find(Consumer.class, consumerId))
                   .orElseThrow(()-> new IllegalArgumentException("consumer not found!"));
           Product product = Optional.ofNullable(em.find(Product.class, productId))
                   .orElseThrow(()->new IllegalArgumentException("product not found!"));
           //на данном этапе у нас точно существует и продукт и покупатель
            BoughtProduct boughtProduct = new BoughtProduct(product, consumer, product.getPrice());//создаём запись о купленном продукте
            em.persist(boughtProduct);//сохраняем запись о покупке в базу
            return boughtProduct;
        });
    }

    /**
     * Метод запускает функцию в транзакции
     * @param transactionalFunction функция, которую нужно выполнить в транзакции
     * @return результат, который вернула функция
     * @param <R> тип результата функции
     */
    private <R> R runInTransaction(Function<EntityManager, R> transactionalFunction) {
        R result;//результат, который мы отсюда вернём
        try (EntityManager em = emf.createEntityManager()) {//получаем менеджер сущностей
            var transaction = em.getTransaction();//получаем транзакцию
            transaction.begin();//начинаем транзакцию
            try {
                result = transactionalFunction.apply(em);//запускаем нашу функцию внутри транзакции
                transaction.commit();//сюда дошли, значит всё ок, коммитим
            } catch (Throwable throwable) {
                //если у нас что-то сломалось, откатываем транзакцию
                transaction.rollback();
                throw throwable;//бросаем дальше, тут мы это обработать не можем
            }
        }
        return result;
    }

    /**
     * Функция запускает переданную ей функцию и передаёт в неё EntityManager
     * Данная функция сама закроет созданный её EntityManager
     * @param entityMangerAcceptor функция, принимающая EntityManger
     * @return результат типа R, который вернула функция entityMangerAcceptor
     * @param <R> тип результата, который должна вернуть функция
     */
    private <R> R runForEntityManager(Function<EntityManager, R> entityMangerAcceptor){
        try (EntityManager em = emf.createEntityManager()) {//получаем менеджер сущностей
            return entityMangerAcceptor.apply(em);
        }
    }
}
