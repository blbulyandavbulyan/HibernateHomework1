package org.blbulyandavbulyan.simplestore.services;

import org.blbulyandavbulyan.simplestore.entites.BoughtItem;
import org.blbulyandavbulyan.simplestore.entites.Consumer;

import java.util.Collection;

/**
 * Данный интерфейс предоставляет абстракцию над магазином
 */
public interface IStore {
    /**
     * Получить купленный покупателем продукты
     * @param name имя покупателя
     * @return коллекцию купленных продуктов или пустую коллекцию, если таковых нет
     */
    Collection<BoughtItem> getBoughtProductsByConsumerName(String name);

    /**
     * Получить покупателей, купивших продукт с заданным именем
     * @param title имя продукта
     * @return коллекция покупателей или пустая коллекция, если товар никто не купил
     */
    Collection<Consumer> getConsumersByProductTitle(String title);

    /**
     * Удаляет покупателя
     * @param name имя покупателя, которого нужно удалить
     * @return true если такой покупатель был, иначе false
     */
    boolean deleteConsumer(String name);

    /**
     * Удаляет продукт по названию
     * @param title название продукта
     * @return true если такой продукт был, иначе false
     */
    boolean deleteProduct(String title);

    /**
     * Покупает продукт
     * @param consumerId ИД покупателя, который должен купить продукт
     * @param productId ИД продукта, который нужно купить
     * @return купленный продукт
     */
    BoughtItem buy(Long consumerId, Long productId);
}
