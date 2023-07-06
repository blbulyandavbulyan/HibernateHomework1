package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Consumer;

public class BuyerRepository extends AbstractRepository<Consumer, Long> {
    public BuyerRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Consumer.class);
    }
}
