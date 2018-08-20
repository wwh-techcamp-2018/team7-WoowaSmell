package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewResponseDto> getLists(Pageable pageable) {
        return reviewRepository.findAllByIsDeleted(pageable, false)
                .stream().map((Review::toReviewDto))
                .collect(Collectors.toList());
    }
}
