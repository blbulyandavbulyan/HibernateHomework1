package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.services.interfaces.IConsumersRepository;

import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runForEntityManager;
import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runInTransaction;

/**
 * Предоставляет реализацию {@link IConsumersRepository} используя для работы с БД EntityManagerFactory
 */
public class ConsumersRepository implements IConsumersRepository {
    /**
     * Фабрика EntityManager, переданная в конструкторе
     */
    private final EntityManagerFactory emf;

    /**
     * Создаёт экземпляр репозитория с переданной EntityManagerFactory
     * @param emf фабрика, которая будет использоваться для работы с БД
     * @throws IllegalArgumentException если фабрика null или закрыта
     */
    public ConsumersRepository(EntityManagerFactory emf) {
        if(emf == null || !emf.isOpen())throw new IllegalArgumentException("entity manager factory is null or closed!");
        this.emf = emf;
    }
    @Override
    public void addConsumer(Consumer consumer) {
        if (consumer == null) throw new IllegalArgumentException("consumer is null!");
        runInTransaction(emf, em -> {
            em.persist(consumer);
            return null;
        });
    }

    @Override
    public boolean existsByName(String name) {
        return runForEntityManager(emf, (em)->{
            var checkExistConsumerQuery = em.createQuery("SELECT COUNT(c) FROM Consumer c WHERE c.name = :name", Long.class);
            checkExistConsumerQuery.setParameter("name", name);
            return checkExistConsumerQuery.getSingleResult() > 0;
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
}
