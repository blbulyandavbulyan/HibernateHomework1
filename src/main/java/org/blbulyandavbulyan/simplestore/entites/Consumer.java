package org.blbulyandavbulyan.simplestore.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Данный класс предоставляет сущность покупателя, у которого есть ИД и имя
 */
@Entity
@Table(name = "buyers")
@NoArgsConstructor
@Getter
@Setter
public class Consumer {
    /**
     * ИД покупателя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumer_id")
    private Long id;
    /**
     * Имя покупателя
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /**
     * Список купленных пользователем товаров
     */
    @OneToMany(mappedBy = "consumer")
    private List<BoughtProduct> boughtProducts;

    public Consumer(String name) {
        this.name = name;
    }
}
