package org.blbulyandavbulyan.simplestore.repositories;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Item;

public class ItemRepository extends AbstractRepository<Item, Long> {
    public ItemRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Item.class);
    }
}
