package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.Restaurant;
import com.woowahan.smell.bazzangee.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    @NotBlank
    private Review review;
    @NotBlank
    private String userName;
    @NotBlank
    private int quantity;
    @NotBlank
    private String foodName;
    @NotBlank
    private Restaurant restaurant;
    @NotBlank
    private LocalDateTime orderedTime;
    @NotBlank
    @DecimalMin("0")
    private int goodCount;

    public ReviewResponseDto(Review review, String userName, int quantity, String foodName, Restaurant restaurant, LocalDateTime orderedTime, int goodCount) {
        this.review = review;
        this.userName = userName;
        this.quantity = quantity;
        this.foodName = foodName;
        this.restaurant = restaurant;
        this.orderedTime = orderedTime;
        this.goodCount = goodCount;
    }
}