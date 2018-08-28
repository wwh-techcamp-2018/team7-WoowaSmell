package com.woowahan.smell.bazzangee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@Builder
public class RestaurantStatisticsDto {

    private String restaurantName;
    private double starPoint;
    private List<Map<String, String>> foodPoints;
}
