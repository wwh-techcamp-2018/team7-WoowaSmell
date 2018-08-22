package com.woowahan.smell.bazzangee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor
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
        if (review == null || review.isDeleted() == true) {
            return false;
        }
        return true;
    }
}