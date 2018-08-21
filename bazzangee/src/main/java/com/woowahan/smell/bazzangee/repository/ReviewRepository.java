package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.FoodCategory;
import com.woowahan.smell.bazzangee.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByIsDeletedFalseOrderByWrittenTimeDesc(Pageable pageable);
    Page<Review> findAllByIsDeletedFalseOrderByStarPointDesc(Pageable pageable);
    Page<Review> findAllByFoodCategoryAndIsDeletedFalseOrderByWrittenTimeDesc(Pageable pageable, FoodCategory foodCategory);
    Page<Review> findAllByFoodCategoryAndIsDeletedFalseOrderByStarPointDesc(Pageable pageable, FoodCategory foodCategory);
}
