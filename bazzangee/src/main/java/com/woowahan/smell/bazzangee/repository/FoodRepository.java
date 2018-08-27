package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
