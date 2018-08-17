package com.woowahan.smell.bazzangee.domain;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(exclude = "imageUrl")
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
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Good> goods;
    @OneToOne
    private OrderFood orderFood;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_food_category"))
    private FoodCategory foodCategory;
    @Column
    @ColumnDefault("false")
    private boolean isDeleted;
    @Column
    private LocalDateTime writtenTime;
    @Column
    private LocalDateTime updatedTime;


}
