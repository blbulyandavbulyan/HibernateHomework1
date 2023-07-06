package org.blbulyandavbulyan.simplestore.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetupDatabase {
    public static void main(String[] args) {
        createTables(ORMUtils.createEntityManagerFactory());
    }
    public static void createTables(EntityManagerFactory entityManagerFactory){
        try {
            executeSqlFromResourceFile(entityManagerFactory, "sql/setup.sql");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dropTables(EntityManagerFactory entityManagerFactory){
        try {
            executeSqlFromResourceFile(entityManagerFactory, "sql/droptables.sql");
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void executeSqlFromResourceFile(EntityManagerFactory entityManagerFactory, String resourceName) throws URISyntaxException, IOException {
        URL sqlFileURL = SetupDatabase.class.getClassLoader().getResource(resourceName);
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
    }
}
