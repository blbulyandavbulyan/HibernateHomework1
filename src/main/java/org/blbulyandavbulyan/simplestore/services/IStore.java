package org.blbulyandavbulyan.simplestore.services;

import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;
import org.blbulyandavbulyan.simplestore.entites.Consumer;
import org.blbulyandavbulyan.simplestore.entites.Product;

import java.util.Collection;

/**
 * Данный интерфейс предоставляет абстракцию над магазином
 */
public interface IStore {
    /**
     * Получить купленный покупателем продукты
     * @param name имя покупателя
     * @return коллекцию купленных продуктов или пустую коллекцию, если таковых нет
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ConsumerNotFoundException если нет покупателя с таким именем
     */
    Collection<BoughtProduct> getBoughtProductsByConsumerName(String name);

    /**
     * Получить покупателей, купивших продукт с заданным именем
     * @param title имя продукта
     * @return коллекция покупателей или пустая коллекция, если товар никто не купил
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ProductNotFoundException если нет товара с таким названием
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
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ConsumerNotFoundException если consumer с consumerId не найден
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ProductNotFoundException если product с productId не найден
     * @return купленный продукт
     */
    BoughtProduct buy(Long consumerId, Long productId);

    /**
     * Добавляет продукт в магазин
     * @param product продукт, который нужно добавить
     */
    void addProduct(Product product);

    /**
     * Добавляет покупателя в магазин
     * @param consumer покупатель, которого нужно добавить
     */
    void addConsumer(Consumer consumer);
}
