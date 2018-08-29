package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.request.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.response.ReviewResponseDto;

public class MockReviewService extends ReviewService{
    @Override
    public ReviewResponseDto create(ReviewRequestDto reviewRequestDto, String url, User loginUser) {
        return ReviewResponseDto.builder()
                .quantity(3)
                .build();
    }
}
