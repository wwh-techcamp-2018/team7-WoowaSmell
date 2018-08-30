package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.food.Restaurant;
import com.woowahan.smell.bazzangee.dto.RestaurantStatisticsDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.repository.food.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticsService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantStatisticsDto getRestaurantInfo(long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 데이터가 존재하지 않습니다."));
        double starPoint = restaurantRepository.getRestaurantStarPoint(id).orElseThrow(() -> new NotMatchException("해당하는 데이터가 존재하지 않습니다."));
        if(starPoint < 0) {
            throw new NotMatchException("해당 지점의 데이터가 없습니다!");
        }
        return RestaurantStatisticsDto.builder()
                .restaurantName(restaurant.getName())
                .starPoint(starPoint)
                .foodPoints(restaurantRepository.getRestaurantFoodStarPoint(id))
                .build();
    }
}
