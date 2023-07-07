package org.blbulyandavbulyan.simplestore.services.interfaces;

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

    /**
     * Проверяет, существует ли покупатель с заданным именем
     * @param name имя покупателя, которого нужно проверить на существование
     * @return true если такой покупатель существует, иначе false
     */
    boolean existsByName(String name);
}
