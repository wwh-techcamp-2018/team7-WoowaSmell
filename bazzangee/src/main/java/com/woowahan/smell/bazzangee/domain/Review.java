package com.woowahan.smell.bazzangee.domain;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import com.woowahan.smell.bazzangee.dto.ReviewDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString(exclude = "imageUrl")
@NoArgsConstructor
@Entity
@Slf4j
public class Review {
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

    public Review(User user, String contents, String imageUrl, double starPoint) {
        this.user = user;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.starPoint = starPoint;
        this.writtenTime = LocalDateTime.now();
    }

    public void delete(User loginUser) {
        if(!loginUser.equals(this.user))
            throw new NotMatchException("글쓴이가 아닙니다.");
        this.isDeleted = true;
    }

    public void update(ReviewDto reviewDto, User loginUser) {
//        if(!this.user.equals(loginUser))
        log.info("loginUser : {}", loginUser);
        log.info("user : {}", this.user);
        if(!loginUser.equals(this.user))
            throw new NotMatchException("타인의 리뷰는 수정할 수 없습니다.");

        this.contents = reviewDto.getContents();
        log.info("contents : {}", contents);
        this.imageUrl = reviewDto.getImage().getOriginalFilename();
        log.info("contents : {}", imageUrl);
        this.starPoint = reviewDto.getStarPoint();
        log.info("contents : {}", starPoint);
    }
}

