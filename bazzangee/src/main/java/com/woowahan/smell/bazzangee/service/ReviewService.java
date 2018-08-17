package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getLists(Pageable pageable) {
        log.info("Service List");

        return reviewRepository.findAllByIsDeleted(pageable, false).getContent();
    }
}
