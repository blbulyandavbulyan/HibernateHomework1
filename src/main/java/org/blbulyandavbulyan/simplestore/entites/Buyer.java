package org.blbulyandavbulyan.simplestore.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "buyer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    /**
     * Имя покупателя
     */
    @Column(name = "name")
    private String name;
}
