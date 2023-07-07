package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.services.impl.Store;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.BeforeEach;

import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.dropTables;

public class StoreTest extends IStoreTest{
    private final static EntityManagerFactory emf = ORMUtils.createEntityManagerFactory();
    @BeforeEach
    void setUp() {
        dropTables(emf);
        createTables(emf);
        store = new Store(emf);
    }
}
