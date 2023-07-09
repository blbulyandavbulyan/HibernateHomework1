package org.blbulyandavbulyan.simplestore.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.blbulyandavbulyan.simplestore.utils.exceptions.FileNotFoundInResources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Данный класс предназначен для первоначальной настройки базы данных
 */
public class SetupDatabase {
    public static void main(String[] args) {
        createTables(ORMUtils.createEntityManagerFactory());
    }

    /**
     * Метод создаёт нужные для работы приложения таблицы
     * @param entityManagerFactory фабрика, с помощью которой будут созданы таблицы
     */
    public static void createTables(EntityManagerFactory entityManagerFactory){
        try {
            executeSqlFromResourceFile(entityManagerFactory, "sql/setup.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод удаляет таблицы, созданные приложением
     * @param entityManagerFactory фабрика, с помощью которой будут удалены таблицы
     */
    public static void dropTables(EntityManagerFactory entityManagerFactory){
        try {
            executeSqlFromResourceFile(entityManagerFactory, "sql/droptables.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод выполняет sql команды из файла в папке с ресурсами
     * @param entityManagerFactory фабрика, от имени которой будут выполнены команды
     * @param resourceName имя файла с ресурсами, в котором содержатся sql команды
     * @throws IOException если возникли проблемы с открытием файла
     * @throws FileNotFoundInResources если переданного файла с командами не существует
     */
    public static void executeSqlFromResourceFile(EntityManagerFactory entityManagerFactory, String resourceName) throws IOException {
        URL sqlFileURL = SetupDatabase.class.getClassLoader().getResource(resourceName);
        if(sqlFileURL != null){
            try(Stream<String> lines = Files.lines(Path.of(sqlFileURL.toURI()))){
                String sql = lines.collect(Collectors.joining());
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                var transaction = entityManager.getTransaction();
                transaction.begin();
                var query = entityManager.createNativeQuery(sql);
                query.executeUpdate();
                transaction.commit();
                entityManager.close();
            }
            catch (URISyntaxException e){
                throw new RuntimeException(e);
            }
        }
        else throw new FileNotFoundInResources("file " + resourceName + " not found in resources");
    }
}
