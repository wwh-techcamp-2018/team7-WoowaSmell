package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "select name, avg(star_point) as avg_point from (select id as food_id, name, restaurant_id, o_id from (select id, restaurant_id, name from food where food.restaurant_id = ?1) f join  (select id as o_id, food_id from order_food) o on o.food_id = f.id) a join review on review.order_food_id = a.o_id group by food_id", nativeQuery = true)
    List<Map<String, String>> getRestaurantFoodStarPoint(Long restaurantId);
}
