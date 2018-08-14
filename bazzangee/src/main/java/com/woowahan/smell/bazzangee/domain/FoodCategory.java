package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private int priority;
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToOne
    private Chat chat;
    @Column
    private String imageUrl;
    @Column
    private String creator;
    @Column
    private LocalDateTime createdTime;
}
