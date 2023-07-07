package org.blbulyandavbulyan.simplestore.services.interfaces;

import org.blbulyandavbulyan.simplestore.entites.Product;

/**
 * Предоставляет интерфейс для хранилища продуктов
 */
public interface IProductsRepository {
    /**
     * Добавляет продукт в хранилище
     * @param product продукт, который нужно добавить
     * @throws IllegalArgumentException если product null
     */
    void addProduct(Product product);
    /**
     * Удаляет продукт по названию
     * @param title название продукта
     * @return true если такой продукт был, иначе false
     * @throws IllegalArgumentException если title null
     */
    boolean deleteProduct(String title);

}
