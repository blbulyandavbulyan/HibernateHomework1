package org.blbulyandavbulyan.simplestore.services;

import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.utils.ORMUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.createTables;
import static org.blbulyandavbulyan.simplestore.utils.SetupDatabase.dropTables;

public class ConsumersRepositoryTest {
    private final static EntityManagerFactory emf = ORMUtils.createEntityManagerFactory();
    private ConsumersRepository consumersRepository;
    @BeforeEach void setUp(){
        dropTables(emf);
        createTables(emf);
        consumersRepository = new ConsumersRepository(emf);
    }
    @Test
    void testDeleteConsumer() {
        // Создание тестовых данных
        Consumer consumer = new Consumer("John");
        // Добавление покупателя
        consumersRepository.addConsumer(consumer);
        // Выполнение метода, который тестируем
        boolean result = consumersRepository.deleteConsumer("John");
        // Проверка результатов
        Assertions.assertTrue(result);
        // TODO: 06.07.2023 добавить проверку что такой consumer удалился из магазина
    }
}
