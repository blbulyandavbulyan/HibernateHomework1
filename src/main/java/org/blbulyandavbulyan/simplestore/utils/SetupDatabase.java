package org.blbulyandavbulyan.simplestore.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class SetupDatabase {
    public static void main(String[] args) throws IOException, URISyntaxException {
        createTables(ORMUtils.createEntityManagerFactory());
    }
    public static void createTables(EntityManagerFactory entityManagerFactory) throws IOException, URISyntaxException {
        executeSqlFromResourceFile(entityManagerFactory, "sql/setup.sql");
    }
    public static void dropTables(EntityManagerFactory entityManagerFactory) throws URISyntaxException, IOException {
        executeSqlFromResourceFile(entityManagerFactory, "sql/droptables.sql");
    }
    public static void executeSqlFromResourceFile(EntityManagerFactory entityManagerFactory, String resourceName) throws URISyntaxException, IOException {
        URL sqlFileURL = SetupDatabase.class.getClassLoader().getResource(resourceName);
        String sql = Files.lines(Path.of(sqlFileURL.toURI())).collect(Collectors.joining());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        var query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
        transaction.commit();
        entityManager.close();
    }
}
