package org.blbulyandavbulyan.simplestore.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Данный класс предоставляет сущность записи о купленном товаре
 */
@Entity
@Table(name = "bought_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoughtProduct {
    /**
     * ИД записи о купленном товаре
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Собственно товар, который купили
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    /**
     * Покупатель, купивший этот товар
     */
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;
    /**
     * Цена на момент покупки
     */
    @Column(name = "price", nullable = false)
    private Long price;

    public BoughtProduct(Product product, Consumer consumer, Long price) {
        this.product = product;
        this.consumer = consumer;
        this.price = price;
    }
}
