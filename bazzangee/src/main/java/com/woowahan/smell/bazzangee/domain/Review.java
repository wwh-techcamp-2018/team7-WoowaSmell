package com.woowahan.smell.bazzangee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.smell.bazzangee.dto.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Slf4j
@Where(clause = "is_deleted != true")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
    private User user;

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

    public Review(OrderFood orderFood, User user, String contents, double starPoint) {
        this.orderFood = orderFood;
        this.user = user;
        this.contents = contents;
        this.starPoint = starPoint;
    }

    public Review(OrderFood orderFood, User user, String contents, double starPoint, String imageUrl) {
        this.orderFood = orderFood;
        this.user = user;
        this.contents = contents;
        this.starPoint = starPoint;
        this.imageUrl = imageUrl;
    }

    public void delete(User loginUser) {
        if (!loginUser.equals(this.user))
            throw new NotMatchException("글쓴이가 아닙니다.");
        this.isDeleted = true;
    }

    public void update(ReviewRequestDto reviewRequestDto, User loginUser) {
        if (!loginUser.equals(this.user))
            throw new NotMatchException("타인의 리뷰는 수정할 수 없습니다.");

        this.contents = reviewRequestDto.getContents();
        if (reviewRequestDto.getImage() != null)
            this.imageUrl = reviewRequestDto.getImage().getOriginalFilename();
        if (reviewRequestDto.getImage() == null)
            this.imageUrl = null;
        this.starPoint = reviewRequestDto.getStarPoint();
    }
    public ReviewResponseDto toReviewDto () {
        return new ReviewResponseDto(this,
                this.orderFood.getOrderedUser().getName(),
                this.orderFood.getQuantity(),
                this.orderFood.getFood().getName(),
                this.orderFood.getFood().getRestaurant(),
                this.orderFood.getOrderTime()
        );
    }
}

