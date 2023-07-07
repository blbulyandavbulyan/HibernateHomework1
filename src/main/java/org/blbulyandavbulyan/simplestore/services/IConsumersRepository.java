package org.blbulyandavbulyan.simplestore.services;

import org.blbulyandavbulyan.simplestore.entites.Consumer;

/**
 * Предоставляет интерфейс для хранилища покупателей
 */
public interface IConsumersRepository {
    /**
     * Удаляет покупателя из хранилища
     * @param name имя покупателя, которого нужно удалить
     * @return true если такой покупатель был, иначе false
     * @throws IllegalArgumentException если name null
     */
    boolean deleteConsumer(String name);
    /**
     * Добавляет покупателя в хранилище
     * @param consumer покупатель, которого нужно добавить
     * @throws IllegalArgumentException если consumer null
     */
    void addConsumer(Consumer consumer);

}
