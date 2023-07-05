package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.BoughtItem;

public class BoughtProductRepository extends AbstractRepository<BoughtItem, Long>{
    public BoughtProductRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, BoughtItem.class);
    }
}
