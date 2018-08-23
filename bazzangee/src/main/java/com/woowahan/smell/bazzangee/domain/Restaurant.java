package com.woowahan.smell.bazzangee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> foods;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_restaurant_parent_restaurant"))
    private Restaurant parentRestaurant;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_restaurant_food_category"))
    private FoodCategory foodCategory;
}