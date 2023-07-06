package org.blbulyandavbulyan.simplestore.entites;

import jakarta.persistence.*;
import lombok.*;

/**
 * Данный класс предоставляет сущность для товара у которого есть ИД, название и текущая цена
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {
    /**
     * ИД товара в базе
     */
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название товара
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    /**
     * Текущая цена
     */
    @Column(name = "price", nullable = false)
    private Long price;
    public Product(String title, Long price) {
        this.title = title;
        this.price = price;
    }
}
