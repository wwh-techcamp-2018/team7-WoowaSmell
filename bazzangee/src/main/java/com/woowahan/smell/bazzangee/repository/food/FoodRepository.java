package com.woowahan.smell.bazzangee.repository.food;

import com.woowahan.smell.bazzangee.domain.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
