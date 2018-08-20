package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.Restaurant;
import com.woowahan.smell.bazzangee.domain.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private Review review;
    private String userName;
    private int quantity;
    private String foodName;
    private Restaurant restaurant;
    private LocalDateTime orderedTime;

    public ReviewResponseDto(Review review, String userName, int quantity, String foodName, Restaurant restaurant, LocalDateTime orderedTime) {
        this.review = review;
        this.userName = userName;
        this.quantity = quantity;
        this.foodName = foodName;
        this.restaurant = restaurant;
        this.orderedTime = orderedTime;
    }
}
