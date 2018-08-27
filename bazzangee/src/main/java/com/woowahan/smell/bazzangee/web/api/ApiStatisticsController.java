package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class ApiStatisticsController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/foods/{id}")
    public List<Map<String, String>> getFoodPointAvg(@PathVariable long id) {
        List<Map<String, String>> result = foodRepository.getRestaurantFoodStarPoint(id);
        result.stream().forEach((v) -> { log.info("object is first : {}, second : {}", v.values().toArray()[0], v.values().toArray()[1]); });
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
