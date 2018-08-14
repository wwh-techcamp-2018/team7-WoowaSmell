package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany
    private List<Food> foods;
    @ManyToOne
    private Restaurant parentRestaurant;
}