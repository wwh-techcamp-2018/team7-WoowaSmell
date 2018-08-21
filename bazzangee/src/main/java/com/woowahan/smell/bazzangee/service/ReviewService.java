package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.repository.OrderFoodRepository;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private OrderFoodRepository orderFoodRepository;

    @Transactional
    public void create(ReviewRequestDto reviewRequestDto, String url, User loginUser) {
        OrderFood orderFood = orderFoodRepository.findById(reviewRequestDto.getOrderFoodId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 내역입니다."));
        reviewRepository.save(reviewRequestDto.toEntity(orderFood, url, loginUser));
    }

    @Transactional
    public void delete(Long id, User loginUser) {
        Review savedReview = getSavedReviewById(id);
        savedReview.delete(loginUser);
    }

    @Transactional
    public void update(Long id, ReviewRequestDto reviewRequestDto, User loginUser) {
        Review savedReview = getSavedReviewById(id);
        savedReview.update(reviewRequestDto, loginUser);
    }

    private Review getSavedReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }

    public List<ReviewResponseDto> getLists(Pageable pageable) {
        return reviewRepository.findAllByIsDeleted(pageable, false)
                .stream().map((Review::toReviewDto))
                .collect(Collectors.toList());
    }
}
