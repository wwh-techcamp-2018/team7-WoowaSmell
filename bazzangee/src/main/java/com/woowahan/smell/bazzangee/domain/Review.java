package com.woowahan.smell.bazzangee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
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
    @JsonIgnore
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


    public ReviewResponseDto toReviewDto() {
        return new ReviewResponseDto(this,
                this.orderFood.getOrderedUser().getName(),
                this.orderFood.getQuantity(),
                this.orderFood.getFood().getName(),
                this.orderFood.getFood().getRestaurant(),
                this.orderFood.getOrderTime()
        );
    }
}
