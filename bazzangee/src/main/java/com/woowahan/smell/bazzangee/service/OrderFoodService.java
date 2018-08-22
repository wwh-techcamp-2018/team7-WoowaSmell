package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.FoodCategory;
import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
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

    public List<OrderFood> getListsOrderByOrderTime(User user) {
        List<OrderFood> orderFoods = orderFoodRepository.findAllByOrderedUserOrderByOrderTimeDesc(user);
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods!");
        }
        return orderFoods;
    }

    public List<OrderFood> getListsByCategoryOrderByOrderTime(User user, Long categoryId) {
        log.info("categoryId : {}", categoryId);
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
        log.info("orderbystarpoint : {}", validOrderFoods.size());
        Collections.sort(validOrderFoods, new StarPointComparator());
        if (validOrderFoods.isEmpty()) {
            throw new NotMatchException("there is no validOrderFoods!");
        }
        return validOrderFoods;
    }

    public List<OrderFood> getListsByCategoryOrderByStarPoint(User user, Long categoryId) {
        log.info("categoryId : {}", categoryId);
        FoodCategory foodCategory = foodCategoryRepository.findById(categoryId).orElseThrow(() -> new NotMatchException("there is no such foodCategory!"));
        List<OrderFood> orderFoods = orderFoodRepository.findAllByOrderedUser(user);
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods!");
        }
        log.info("bycategoryorderbystarpoint3 : {}", orderFoods.size());
        orderFoods = orderFoods
                .stream()
                .filter(orderFood -> orderFood.hasValidReview())
                .map((v) -> { log.info("bycategoryorderbystarpoint2 : {}", v.getId()); return v;})
                .filter(orderFood -> orderFood.getReview().getFoodCategory().equals(foodCategory))
                .collect(Collectors.toList());
        log.info("bycategoryorderbystarpoint : {}", orderFoods.size());
        Collections.sort(orderFoods, new StarPointComparator());
        if (orderFoods.isEmpty()) {
            throw new NotMatchException("there is no OrderFoods by this category!");
        }
        return orderFoods;
    }
}
