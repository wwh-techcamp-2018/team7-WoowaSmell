package com.woowahan.smell.bazzangee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodResponseDto {
    private Long writerId;
    private String userName;
    private String reviewTitle;
    private int goodCount;
    private String message;

    private GoodResponseDto(String message) {
        this.message = message;
    }

    public GoodResponseDto(int goodCount, String message) {
        this.goodCount = goodCount;
        this.message = message;
    }

    public GoodResponseDto(Long writerId, String userName, String reviewTitle, int goodCount) {
        this.writerId = writerId;
        this.userName = userName;
        this.reviewTitle = reviewTitle;
        this.goodCount = goodCount;
    }

    public static GoodResponseDto ofString (String message) {
        return new GoodResponseDto(message);
    }

    public GoodResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public GoodResponseDto setWriterId(Long writerId) {
        this.writerId = writerId;
        return this;
    }

    public GoodResponseDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public GoodResponseDto setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
        return this;
    }

    public GoodResponseDto setGoodCount(int goodCount) {
        this.goodCount = goodCount;
        return this;
    }
}
