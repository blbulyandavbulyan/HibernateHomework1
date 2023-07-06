package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;

public class BoughtProductRepository extends AbstractRepository<BoughtProduct, Long>{
    public BoughtProductRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, BoughtProduct.class);
    }
}
