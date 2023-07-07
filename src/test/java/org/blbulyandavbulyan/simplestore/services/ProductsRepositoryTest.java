package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Product;
import org.blbulyandavbulyan.simplestore.services.impl.ProductsRepository;
import org.blbulyandavbulyan.simplestore.services.interfaces.IProductsRepository;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.dropTables;

public class ProductsRepositoryTest {
    private IProductsRepository iProductsRepository;
    private final static EntityManagerFactory emf = ORMUtils.createEntityManagerFactory();
    @BeforeEach
    void setUp() {
        dropTables(emf);
        createTables(emf);
        iProductsRepository = new ProductsRepository(emf);
    }
    @Test
    void testDeleteProduct() {
        // Создание тестовых данных
        Product product = new Product("Apple", 299L);
        // Добавление продукта
        iProductsRepository.addProduct(product);
        // Выполнение метода, который тестируем
        boolean result = iProductsRepository.deleteProduct("Apple");
        // Проверка результатов
        Assertions.assertTrue(result);
        // TODO: 06.07.2023 добавить проверку что такой product действительно удалился из магазина
    }
}
