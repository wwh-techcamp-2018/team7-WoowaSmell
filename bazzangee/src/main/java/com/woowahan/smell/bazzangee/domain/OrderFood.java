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
    @Column
    private User orderedUser;
    @OneToOne
    private Review review;
    @Column
    private LocalDateTime orderTime;
}