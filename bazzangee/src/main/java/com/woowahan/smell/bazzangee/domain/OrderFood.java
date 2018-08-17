package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column
    private LocalDateTime orderTime;
}