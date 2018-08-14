package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    @Lob
    private String contents;
    @Column
    private String imageUrl;
    @Column
    private double starPoint;
    @OneToMany
    private List<Like> likes;
    @Column
    private boolean isDeleted;
    @Column
    private LocalDateTime writtenTime;
    @Column
    private LocalDateTime updatedTime;
}
