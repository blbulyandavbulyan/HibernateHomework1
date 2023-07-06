package org.blbulyandavbulyan.simplestore.utils;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class ORMUtils {
    public static EntityManagerFactory createEntityManagerFactory(){
        return new Configuration().configure("configs/hibernate.cfg.xml").buildSessionFactory();
    }
}
