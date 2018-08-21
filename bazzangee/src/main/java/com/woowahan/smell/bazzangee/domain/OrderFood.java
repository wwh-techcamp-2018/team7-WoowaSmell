package com.woowahan.smell.bazzangee.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class OrderFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Food food;
    @Column
    private int quantity;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_food_user"))
    private User orderedUser;
    @OneToOne(mappedBy = "orderFood")
    private Review review;
    @Column
    private LocalDateTime orderTime;
}