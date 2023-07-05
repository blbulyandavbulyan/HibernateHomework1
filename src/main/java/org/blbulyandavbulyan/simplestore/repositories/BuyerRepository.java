package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Buyer;

public class BuyerRepository extends AbstractRepository<Buyer, Long> {
    public BuyerRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Buyer.class);
    }
}
