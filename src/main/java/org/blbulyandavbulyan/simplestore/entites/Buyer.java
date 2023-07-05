package org.blbulyandavbulyan.simplestore.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Данный класс предоставляет сущность покупателя, у которого есть ИД и имя
 */
@Entity
@Table(name = "buyers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Buyer {
    /**
     * ИД покупателя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Long id;
    /**
     * Имя покупателя
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * Список купленных пользователем товаров
     */
    @OneToMany(mappedBy = "buyer")
    private List<BoughtItem> boughtItems;
}
