package org.blbulyandavbulyan.simplestore.services.interfaces;

import org.blbulyandavbulyan.simplestore.entites.BoughtProduct;
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
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ConsumerNotFoundException если нет покупателя с таким именем
     * @throws IllegalArgumentException если name null
     */
    Collection<BoughtProduct> getBoughtProductsByConsumerName(String name);

    /**
     * Получить покупателей, купивших продукт с заданным именем
     * @param title имя продукта
     * @return коллекция покупателей или пустая коллекция, если товар никто не купил
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ProductNotFoundException если нет товара с таким названием
     * @throws IllegalArgumentException если title null
     */
    Collection<Consumer> getConsumersByProductTitle(String title);

    /**
     * Покупает продукт
     * @param consumerId ИД покупателя, который должен купить продукт
     * @param productId ИД продукта, который нужно купить
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ConsumerNotFoundException если consumer с consumerId не найден
     * @throws org.blbulyandavbulyan.simplestore.services.exceptions.ProductNotFoundException если product с productId не найден
     * @throws IllegalArgumentException если consumerId или productId null
     * @return купленный продукт
     */
    BoughtProduct buy(Long consumerId, Long productId);

    /**
     * @return экземпляр {@link IConsumersRepository}, который связан с данным магазином
     */
    IConsumersRepository getConsumerRepository();
    /**
     * @return экземпляр {@link IProductsRepository}, который связан с данным магазином
     */
    IProductsRepository getProductRepository();
}
