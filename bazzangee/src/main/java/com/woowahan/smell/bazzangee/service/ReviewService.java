package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.Good;
import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.request.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.response.GoodResponseDto;
import com.woowahan.smell.bazzangee.dto.response.ReviewResponseDto;
import com.woowahan.smell.bazzangee.repository.GoodRepository;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.repository.FoodCategoryRepository;
import com.woowahan.smell.bazzangee.repository.OrderFoodRepository;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {
    private final String UNLIKE = "UNLIKE";
    private final String LIKE   = "LIKE";

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private OrderFoodRepository orderFoodRepository;

    @Transactional
    public ReviewResponseDto create(ReviewRequestDto reviewRequestDto, String url, User loginUser) {
        OrderFood orderFood = orderFoodRepository.findById(reviewRequestDto.getOrderFoodId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 내역입니다."));
        return reviewRepository.save(reviewRequestDto.toEntity(orderFood, url, loginUser)).toReviewDto();
    }

    @Transactional
    public OrderFood delete(Long orderFoodId, User loginUser) {
        OrderFood orderFood = orderFoodRepository.findById(orderFoodId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 내역입니다."));
        Review savedReview = getSavedReviewById(orderFood.getReview().getId());
        savedReview.delete(loginUser);
        return orderFood;
    }


    @Transactional
    public OrderFood update(Long orderFoodId, String url, ReviewRequestDto reviewRequestDto, User loginUser) {
        OrderFood orderFood = orderFoodRepository.findById(orderFoodId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 내역입니다."));
        Review savedReview = getSavedReviewById(orderFood.getReview().getId());
        savedReview.update(reviewRequestDto, loginUser, url);
        return orderFood;
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

    public List<ReviewResponseDto> getListsOrderByGoodsCount(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByIsDeletedFalseOrderByGoodsSIZE(pageable);
        return reviews.getContent()
                .stream()
                .map(Review::toReviewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GoodResponseDto updateGood(Long id, User sessionUser) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 리뷰가 없습니다."));
        Optional<Good> optionalGood = review.getGoods().stream()
                .filter((good) -> good.matchUser(sessionUser))
                .findFirst();

        return GoodResponseDto.ofString(updateGood(sessionUser, review, optionalGood))
                .setReviewTitle(review.getOrderFood().getFood().getName())
                .setUserName(sessionUser.getName())
                .setWriterId(review.getUser().getId())
                .setGoodCount(review.getGoods().size());
    }

    private String updateGood(User sessionUser, Review review, Optional<Good> optionalGood) {
        // 이미 좋아요를 눌렀던 사용자일 경우
        if (optionalGood.isPresent()) {
            review.removeGood(optionalGood.get());
            goodRepository.delete(optionalGood.get());
            return UNLIKE;
        }

        // 좋아요를 누르지 않았던 사용자일 경우
        review.addGood(new Good(sessionUser, review));
        return LIKE;
    }

    public List<ReviewResponseDto> getListsByCategoryOrderByWrittenTime(Pageable pageable, Long categoryId) {
        Page<Review> reviews = reviewRepository.findAllByFoodCategoryAndIsDeletedFalseOrderByWrittenTimeDesc(pageable, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!")));
        log.info("first\n");
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream()
                .map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getListsByCategoryOrderByStarPoint(Pageable pageable, Long categoryId) {
        Page<Review> reviews = reviewRepository.findAllByFoodCategoryAndIsDeletedFalseOrderByStarPointDesc(pageable, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!")));
        if (!reviews.hasContent()) {
            throw new NotMatchException("there is no Reviews!");
        }
        return reviews
                .stream()
                .map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getListsByCategoryOrderByGoodsCount(Pageable pageable, Long categoryId) {
        Page<Review> reviews = reviewRepository.findAllByFoodCategoryAndIsDeletedFalseOrderByGoodsSIZEDesc(pageable, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("선택하신 음식 카테고리가 존재하지 않습니다.!")));
        return reviews
                .stream()
                .map((Review::toReviewDto))
                .collect(Collectors.toList());
    }

    public OrderFood getReviewOne(Long orderId) {
        return orderFoodRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
    }
}
