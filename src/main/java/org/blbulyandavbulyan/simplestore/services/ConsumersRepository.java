package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.services.interfaces.IConsumersRepository;

import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runForEntityManager;
import static org.blbulyandavbulyan.simplestore.utils.ORMUtils.runInTransaction;

public class ConsumersRepository implements IConsumersRepository {
    private final EntityManagerFactory emf;
    public ConsumersRepository(EntityManagerFactory emf) {
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
