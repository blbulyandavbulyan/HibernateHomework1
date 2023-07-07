package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.blbulyandavbulyan.simplestore.entites.Product;
import org.blbulyandavbulyan.simplestore.services.interfaces.IProductsRepository;

import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runForEntityManager;
import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runInTransaction;

/**
 * Предоставляет {@link IProductsRepository}, используя для своей работы EntityManagerFactory
 */
public class ProductsRepository implements IProductsRepository {
    /**
     * Фабрика EntityManager, которая будет использоваться для работы с БД
     */
    private final EntityManagerFactory emf;

    /**
     * Создаёт экземпляр с заданной EntityManagerFactory
     * @param emf фабрика, для создания EntityManager
     */
    public ProductsRepository(EntityManagerFactory emf) {
        this.emf = emf;
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
    public boolean deleteProduct(String title) {
        if (title == null) throw new IllegalArgumentException("title is null!");
        return runInTransaction(emf, (em) -> {
            Query deleteProductQuery = em.createQuery("DELETE FROM Product p WHERE p.title = :title");
            int result = deleteProductQuery.setParameter("title", title).executeUpdate();
            return result > 0;//если result > 0 значит были затронуты записи запросом
        });
    }

    @Override
    public boolean existsByTitle(String title) {
        return runForEntityManager(emf, em -> {
            var checkExistConsumerQuery = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.title = :title", Long.class);
            checkExistConsumerQuery.setParameter("title", title);
            return checkExistConsumerQuery.getSingleResult() > 0;
        });
    }
}
