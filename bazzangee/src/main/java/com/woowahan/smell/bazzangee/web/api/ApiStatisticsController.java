package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.dto.RestaurantStatisticsDto;
import com.woowahan.smell.bazzangee.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class ApiStatisticsController {

    @Autowired
    private StatisticsService statisticsService;
//
//    @GetMapping("/restaurants/foods/{id}")
//    public List<Map<String, String>> getFoodsInRestaurantPointAvg(@PathVariable long id) {
//        List<Map<String, String>> result = statisticsService.getRestaurantFoodsInfo(id);
//        result.stream().forEach((v) -> { log.info("object is first : {}, second : {}", v.values().toArray()[0], v.values().toArray()[1]); });
//        return result;
//    }

    @GetMapping("/restaurants/{id}")
    public RestaurantStatisticsDto getRestaurantPointAvg(@PathVariable long id) {
        RestaurantStatisticsDto result = statisticsService.getRestaurantInfo(id);
        log.info("here : ");
//        result.stream().forEach((v) -> { log.info("object is first : {}, second : {}", v.values().toArray()[0], v.values().toArray()[1]); });
        return result;
    }
//
//    @GetMapping("/food/{id}")
//    public Object getFoodName(@PathVariable long id) {
//        Object result = foodRepository.getnameofFood(id);
//        log.info("object is : {}", result);
//        return result;
//    }

}
