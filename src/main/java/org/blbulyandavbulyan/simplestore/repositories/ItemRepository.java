package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Product;

public class ItemRepository extends AbstractRepository<Product, Long> {
    public ItemRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Product.class);
    }
}
