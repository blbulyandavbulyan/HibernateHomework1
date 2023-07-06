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
public class BoughtItem {
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
    @JoinColumn(name = "item_id")
    private Item boughtItem;
    /**
     * Покупатель, купивший этот товар
     */
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Consumer consumer;
    /**
     * Цена на момент покупки
     */
    @Column(name = "price", nullable = false)
    private Long price;

    public BoughtItem(Item boughtItem, Consumer consumer, Long price) {
        this.boughtItem = boughtItem;
        this.consumer = consumer;
        this.price = price;
    }
}
