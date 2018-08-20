package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Page<Review> findAllByIsDeleted(Pageable pageable, boolean isDeleted);
}
