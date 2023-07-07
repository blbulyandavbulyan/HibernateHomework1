package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.dropTables;

public class ConsumerRepositoryTest {
    private final static EntityManagerFactory emf = ORMUtils.createEntityManagerFactory();
    private ConsumerRepository consumerRepository;
    @BeforeEach void setUp(){
        dropTables(emf);
        createTables(emf);
        consumerRepository = new ConsumerRepository(emf);
    }
    @Test
    void testDeleteConsumer() {
        // Создание тестовых данных
        Consumer consumer = new Consumer("John");
        // Добавление покупателя
        consumerRepository.addConsumer(consumer);
        // Выполнение метода, который тестируем
        boolean result = consumerRepository.deleteConsumer("John");
        // Проверка результатов
        Assertions.assertTrue(result);
        // TODO: 06.07.2023 добавить проверку что такой consumer удалился из магазина
    }
}
