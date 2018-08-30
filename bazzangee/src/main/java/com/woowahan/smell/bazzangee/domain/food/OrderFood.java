package com.woowahan.smell.bazzangee.domain.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.smell.bazzangee.domain.contents.Review;
import com.woowahan.smell.bazzangee.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@ToString(exclude = "review")
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
    @JsonIgnore
    private User orderedUser;
    @OneToOne(mappedBy = "orderFood")
    private Review review;
    @Column
    private LocalDateTime orderTime;

    public String getWrittenDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = this.orderTime.format(formatter);

        return formatDateTime.split(" ")[0];
    }

    public String getWrittenTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formatDateTime = this.orderTime.format(formatter);

        String datatime[] = formatDateTime.split(" ")[1].split(":");
        return datatime[0] + ":" + datatime[1];
    }

    public boolean isMatchedBy(User reviewUser) {
        return this.orderedUser.equals(reviewUser);
    }

    public boolean hasValidReview() {
        if (this.review == null || this.review.isDeleted() == true) {
            log.info("this is false review : {}, {}", this.review, this.toString());
            return false;
        }
        log.info("this is true review :{}, {}", this.review, this.toString());
        return true;
    }
}