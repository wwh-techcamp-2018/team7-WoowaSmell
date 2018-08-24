package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.FoodCategory;
import com.woowahan.smell.bazzangee.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByIsDeletedFalseOrderByWrittenTimeDesc(Pageable pageable);
    Page<Review> findAllByIsDeletedFalseOrderByStarPointDesc(Pageable pageable);
    @Query("SELECT r FROM Review r ORDER BY SIZE (r.goods) DESC")
    Page<Review> findAllByIsDeletedFalseOrderByGoodsSIZE(Pageable pageable);

    Page<Review> findAllByFoodCategoryAndIsDeletedFalseOrderByWrittenTimeDesc(Pageable pageable, FoodCategory foodCategory);
    Page<Review> findAllByFoodCategoryAndIsDeletedFalseOrderByStarPointDesc(Pageable pageable, FoodCategory foodCategory);
    @Query("SELECT r FROM Review r WHERE r.foodCategory = :foodCategory ORDER BY SIZE (r.goods) DESC")
    Page<Review> findAllByFoodCategoryAndIsDeletedFalseOrderByGoodsSIZEDesc(Pageable pageable, @Param("foodCategory") FoodCategory foodCategory);
}
