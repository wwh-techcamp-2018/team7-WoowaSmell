package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.OrderFoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.getUserFromSession;

@Slf4j
@RestController
@RequestMapping("/api/orderfoods")
public class ApiOrderFoodController {

    @Autowired
    private OrderFoodService orderFoodService;

    @GetMapping("")
    public ResponseEntity<List> getReviewListOfUser(Long filterId, HttpSession session) {
        User user = getUserFromSession(session);
        log.info("getOrderFoodList : {}", user);
        if(user == null) {
            throw new UnAuthenticationException("Session이 존재하지 않습니다.");
        }
        if(filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(orderFoodService.getListsOrderByOrderTime(user));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderFoodService.getListsOrderByStarPoint(user));
    }

    @GetMapping("/categories")
    public ResponseEntity<List> getReviewListOfUserByCategory(Long categoryId, Long filterId, HttpSession session) {
        User user = getUserFromSession(session);
        log.info("getOrderFoodList : {}", user);
        if(filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(orderFoodService.getListsByCategoryOrderByOrderTime(user, categoryId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderFoodService.getListsByCategoryOrderByStarPoint(user, categoryId));
    }
}
