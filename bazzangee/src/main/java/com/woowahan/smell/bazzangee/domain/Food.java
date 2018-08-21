package com.woowahan.smell.bazzangee.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_food_restaurant"))
    private Restaurant restaurant;

    @Column
    private String imageUrl;
}
