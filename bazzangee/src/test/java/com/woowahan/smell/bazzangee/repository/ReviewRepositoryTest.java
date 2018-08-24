package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.vo.PageVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewRepositoryTest {
   private static final Logger log = LoggerFactory.getLogger(ReviewRepositoryTest.class);
    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void 좋아요_많은_순으로_정렬() {
        PageVO pageVO = new PageVO();
        List<Review> reviews = reviewRepository.findAllByIsDeletedFalseOrderByGoodsSIZE(pageVO.makePageable(Sort.Direction.DESC.ordinal(), "id")).getContent();
        for (Review review : reviews) {
            log.info("review contents : {}", review.getContents());
        }
    }
}
