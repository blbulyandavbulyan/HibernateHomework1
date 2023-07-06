package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.dropTables;

public class StoreTest extends IStoreTest{
    private final static EntityManagerFactory emf = ORMUtils.createEntityManagerFactory();
    @BeforeEach
    void setUp() {
        try {
            dropTables(emf);
            createTables(emf);
            store = new Store(emf);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
