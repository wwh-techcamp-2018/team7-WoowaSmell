package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.Good;
import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.repository.GoodRepository;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.repository.FoodCategoryRepository;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private GoodRepository goodRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Transactional
    public void create(ReviewRequestDto reviewRequestDto, String url, User loginUser) {
        reviewRepository.save(reviewRequestDto.toEntity(url, loginUser));
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

    public List<ReviewResponseDto> getListsOrderByWrittenTime(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByIsDeletedFalseOrderByWrittenTimeDesc(pageable);
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream().map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getListsOrderByStarPoint(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByIsDeletedFalseOrderByStarPointDesc(pageable);
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream().map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDto updateGood(Long id, User sessionUser){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 리뷰가 없습니다."));
        List<Good> goods = review.getGoods().stream().filter((good) -> good.matchUser(sessionUser)).collect(Collectors.toList());

        // 이미 좋아요를 눌렀던 사용자일 경우
        if (goods.size() > 0) {
            review.removeGood(goods.get(0));
            goodRepository.delete(goods.get(0));
        } else {
            review.addGood(new Good(sessionUser, review));
        }
        // 좋아요를 누르지 않았던 사용자일 경우
        return review.toReviewDto();
    }

    public List<ReviewResponseDto> getListsByCategoryOrderByWrittenTime(Pageable pageable, Long categoryId) {
        log.info("foodCagtegory : {}", foodCategoryRepository.findById(categoryId).get());
        Page<Review> reviews = reviewRepository.findAllByFoodCategoryAndIsDeletedFalseOrderByWrittenTimeDesc(pageable, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!")));
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream()
                .map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getListsByCategoryOrderByStarPoint(Pageable pageable, Long categoryId) {
        log.info("foodCagtegory : {}", foodCategoryRepository.findById(categoryId).get());
        Page<Review> reviews = reviewRepository.findAllByFoodCategoryAndIsDeletedFalseOrderByStarPointDesc(pageable, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!")));
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream()
                .map((Review::toReviewDto))
                .collect(Collectors.toList());
    }
}
