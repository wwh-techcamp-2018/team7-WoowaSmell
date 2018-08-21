package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderFoodRepository extends CrudRepository<OrderFood, Long> {
    Optional<List<OrderFood>> findByOrderedUser(User user);
}
