package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.entites.Product;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public abstract class IStoreTest {
    protected IStore store;
    @BeforeEach
    abstract void setUp();

    @Test
    void testGetBoughtProductsByConsumerName() {
        // Создание тестовых данных
        Consumer consumer = new Consumer("John");
        Product product1 = new Product("Apple", 299L);
        Product product2 = new Product("Banana", 199L);
        // Добавление купленных продуктов
        store.addProduct(product1);
        store.addProduct(product2);
        store.addConsumer(consumer);
        BoughtProduct boughtProduct1 = store.buy(consumer.getId(), product1.getId());
        BoughtProduct boughtProduct2 = store.buy(consumer.getId(), product2.getId());

        // Выполнение метода, который тестируем
        Collection<BoughtProduct> boughtProducts = store.getBoughtProductsByConsumerName("John");

        // Проверка результатов
        Assertions.assertEquals(2, boughtProducts.size());
        Assertions.assertTrue(boughtProducts.contains(boughtProduct1));
        Assertions.assertTrue(boughtProducts.contains(boughtProduct2));
    }

    @Test
    void testGetConsumersByProductTitle() {
        // Создание тестовых данных
        Product product = new Product("Apple", 299L);
        Consumer consumer1 = new Consumer("John");
        Consumer consumer2 = new Consumer("Alice");

        BoughtProduct boughtProduct1 = new BoughtProduct(product, consumer1, 299L);
        BoughtProduct boughtProduct2 = new BoughtProduct(product, consumer2, 299L);

        // Добавление купленных продуктов
        store.addProduct(product);
        store.addConsumer(consumer1);
        store.addConsumer(consumer2);
        store.buy(consumer1.getId(), product.getId());
        store.buy(consumer2.getId(), product.getId());

        // Выполнение метода, который тестируем
        Collection<Consumer> consumers = store.getConsumersByProductTitle("Apple");

        // Проверка результатов
        Assertions.assertEquals(2, consumers.size());
        Assertions.assertTrue(consumers.contains(consumer1));
        Assertions.assertTrue(consumers.contains(consumer2));
    }

//    @Test
//    void testDeleteConsumer() {
//        // Создание тестовых данных
//        Consumer consumer = new Consumer("John");
//
//        // Добавление покупателя
//        store.addConsumer(consumer);
//
//        // Выполнение метода, который тестируем
//        boolean result = store.deleteConsumer("John");
//
//        // Проверка результатов
//        Assertions.assertTrue(result);
//        Assertions.assertEquals(0, store.getAllConsumers().size());
//    }
//    @Test
//    void testDeleteProduct() {
//        // Создание тестовых данных
//        Product product = new Product("Apple", 299L);
//
//        // Добавление продукта
//        store.addProduct(product);
//
//        // Выполнение метода, который тестируем
//        boolean result = store.deleteProduct("Apple");
//
//        // Проверка результатов
//        Assertions.assertTrue(result);
//        Assertions.assertEquals(0, store.getAllProducts().size());
//    }
}
