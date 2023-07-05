package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.metamodel.EntityType;

import java.util.Collection;
import java.util.Set;

abstract class AbstractRepository<ET, IDTYPE> {
    protected EntityManagerFactory entityManagerFactory;
    protected Class<ET> entityType;
    protected AbstractRepository(EntityManagerFactory entityManagerFactory, Class<ET> entityType) {
        if(entityManagerFactory == null)throw new IllegalArgumentException("entityMangerFactory is null!");
        if(!isEntity(entityManagerFactory, entityType))throw new IllegalArgumentException("Entity type is not an valid JPA entity!");
        this.entityManagerFactory = entityManagerFactory;
        this.entityType = entityType;
    }
    public void add(ET entity){
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.persist(entity);
        }
    }
    public void addAll(Collection<ET> entities){
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            var transaction = entityManager.getTransaction();
            transaction.begin();
            for(ET entity : entities){
                entityManager.persist(entity);
            }
            transaction.commit();
        }
    }
    public ET deleteById(IDTYPE id){
        ET entityForDelete;
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityForDelete = entityManager.find(entityType, id);
            entityManager.remove(entityForDelete);
        }
        return entityForDelete;
    }
    public ET findById(IDTYPE id){
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(entityType, id);
        }
    }
    private static boolean isEntity(EntityManagerFactory entityManagerFactory, Class<?> clazz) {
        boolean foundEntity = false;
        Set<EntityType<?>> entities = entityManagerFactory.getMetamodel().getEntities();
        for(EntityType<?> entityType :entities) {
            Class<?> entityClass = entityType.getJavaType();
            if(entityClass.equals(clazz)) {
                foundEntity = true;
            }
        }
        return foundEntity;
    }
}
