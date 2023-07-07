package org.blbulyandavbulyan.simplestore.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

import java.util.function.Function;

public class ORMUtils {
    public static EntityManagerFactory createEntityManagerFactory(){
        return new Configuration().configure("configs/hibernate.cfg.xml").buildSessionFactory();
    }
    /**
     * Метод запускает функцию в транзакции и передаёт ей созданный EntityManager
     *
     * @param emf                   EntityManagerFactory, которая будет использоваться для создания EntityManager
     * @param transactionalFunction функция, которую нужно выполнить в транзакции
     * @param <R>                   тип результата функции
     * @return результат, который вернула функция
     */
    public static  <R> R runInTransaction(EntityManagerFactory emf, Function<EntityManager, R> transactionalFunction) {
        R result;//результат, который мы отсюда вернём
        try (EntityManager em = emf.createEntityManager()) {//получаем менеджер сущностей
            var transaction = em.getTransaction();//получаем транзакцию
            transaction.begin();//начинаем транзакцию
            try {
                result = transactionalFunction.apply(em);//запускаем нашу функцию внутри транзакции
                transaction.commit();//сюда дошли, значит всё ок, коммитим
            } catch (Exception throwable) {
                //если у нас что-то сломалось, откатываем транзакцию
                transaction.rollback();
                throw throwable;//бросаем дальше, тут мы это обработать не можем
            }
        }
        return result;
    }
    /**
     * Функция запускает переданную ей функцию и передаёт в неё EntityManager
     * Данная функция сама закроет созданный её EntityManager
     * @param emf фабрика, которая будет использоваться для создания EntityManager
     * @param entityMangerAcceptor функция, принимающая EntityManger
     * @param <R>                  тип результата, который должна вернуть функция
     * @return результат типа R, который вернула функция entityMangerAcceptor
     */
    public static  <R> R runForEntityManager(EntityManagerFactory emf, Function<EntityManager, R> entityMangerAcceptor) {
        try (EntityManager em = emf.createEntityManager()) {//получаем менеджер сущностей
            return entityMangerAcceptor.apply(em);
        }
    }
}
