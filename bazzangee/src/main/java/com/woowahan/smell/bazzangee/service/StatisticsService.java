package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Map<String,String>> getRestaurantFoodsInfo(long id) {
        return restaurantRepository.getRestaurantFoodStarPoint(id);
    }

    public List<Map<String,String>> getRestaurantInfo(long id) {
        return restaurantRepository.getRestaurantStarPoint(id);
    }
}
