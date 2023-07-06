package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.metamodel.EntityType;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

abstract class AbstractRepository<ET, IDTYPE> {
    protected EntityManagerFactory entityManagerFactory;
    protected Class<ET> entityType;

    protected AbstractRepository(EntityManagerFactory entityManagerFactory, Class<ET> entityType) {
        if (entityManagerFactory == null) throw new IllegalArgumentException("entityMangerFactory is null!");
        if (!isEntity(entityManagerFactory, entityType))
            throw new IllegalArgumentException("Entity type is not an valid JPA entity!");
        this.entityManagerFactory = entityManagerFactory;
        this.entityType = entityType;
    }

    public void add(ET entity) {
        runInTransaction((em)->{
            em.persist(entity);
        });
    }

    public void addAll(Collection<ET> entities) {
        runInTransaction((em) -> {
            for (ET entity : entities) {
                em.persist(entity);
            }
        });
    }

    public ET deleteById(IDTYPE id) {
        ET entityForDelete;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityForDelete = entityManager.find(entityType, id);
            entityManager.remove(entityForDelete);
        }
        return entityForDelete;
    }

    public ET findById(IDTYPE id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(entityType, id);
        }
    }

    private static boolean isEntity(EntityManagerFactory entityManagerFactory, Class<?> clazz) {
        Set<EntityType<?>> entities = entityManagerFactory.getMetamodel().getEntities();
        for (EntityType<?> entityType : entities) {
            Class<?> entityClass = entityType.getJavaType();
            if (entityClass.equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    private void runInTransaction(Consumer<EntityManager> r) {
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            var transaction = em.getTransaction();
            transaction.begin();
            try {
                r.accept(em);
                transaction.commit();
            } catch (Throwable throwable) {
                transaction.rollback();
                throw throwable;
            }
        }
    }
}
