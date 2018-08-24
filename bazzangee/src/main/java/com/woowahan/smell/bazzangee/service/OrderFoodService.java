package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.FoodCategory;
import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.repository.FoodCategoryRepository;
import com.woowahan.smell.bazzangee.repository.OrderFoodRepository;
import com.woowahan.smell.bazzangee.utils.StarPointComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderFoodService {
    @Autowired
    private OrderFoodRepository orderFoodRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    public List<OrderFood> getList(User loginUser) {
        return orderFoodRepository.findByOrderedUser(loginUser)
                .orElseThrow(() -> new UnAuthenticationException("주문 내역이 없습니다."));
    }

    public List<OrderFood> getListsOrderByOrderTime(User user) {
        List<OrderFood> orderFoods = orderFoodRepository.findAllByOrderedUserOrderByOrderTimeDesc(user);
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods!");
        }
        return orderFoods;
    }

    public List<OrderFood> getListsByCategoryOrderByOrderTime(User user, Long categoryId) {
        FoodCategory foodCategory = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!"));
        List<OrderFood> orderFoods = orderFoodRepository.findAllByOrderedUserOrderByOrderTimeDesc(user);
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods!");
        }
        orderFoods = orderFoods
                .stream()
                .filter(orderFood -> { return orderFood.getFood().getRestaurant().getFoodCategory().equals(foodCategory);})
                .collect(Collectors.toList());
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods by this category!");
        }
        return orderFoods;
    }

    public List<OrderFood> getListsOrderByStarPoint(User user) {
        List<OrderFood> validOrderFoods = orderFoodRepository.findAllByOrderedUser(user)
                .stream()
                .filter(orderFood -> orderFood.hasValidReview())
                .collect(Collectors.toList());
        Collections.sort(validOrderFoods, new StarPointComparator());
        if (validOrderFoods.isEmpty()) {
            throw new NotMatchException("there is no validOrderFoods!");
        }
        return validOrderFoods;
    }

//    public List<OrderFood> getListsOrderByGoodsCount(User user) {
//        List<OrderFood> validOrderFoods = orderFoodRepository.findAllByOrderedUser(user)
//                .stream()
//                .filter(orderFood -> orderFood.hasValidReview())
//
//                .collect(Collectors.toList());
//        Collections.sort(validOrderFoods, new StarPointComparator());
//        if (validOrderFoods.isEmpty()) {
//            throw new NotMatchException("there is no validOrderFoods!");
//        }
//        return validOrderFoods;
//    }

    public List<OrderFood> getListsByCategoryOrderByStarPoint(User user, Long categoryId) {
        FoodCategory foodCategory = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!"));
        List<OrderFood> orderFoods = orderFoodRepository.findAllByOrderedUser(user);
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods!");
        }
        log.info("orderFoods : {}", orderFoods);
        orderFoods = orderFoods
                .stream()
                .filter(orderFood -> orderFood.hasValidReview() && orderFood.getReview().getFoodCategory().equals(foodCategory))
                .collect(Collectors.toList());
        Collections.sort(orderFoods, new StarPointComparator());
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods by this category!");
        }
        return orderFoods;
    }

    public List<OrderFood> getListsOrderByGoodsCount(User user) {
        return orderFoodRepository.findAllByOrderedUserOrderByGoodCountDesc(user);
    }

    public List<OrderFood> getListsByCategoryOrderByGoodsCount(User user, Long categoryId) {
        return orderFoodRepository.findAllByCategoryOrderByGoodsCount(user, foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("선택하신 음식 카테고리가 존재하지 않습니다.!")));
    }
}
