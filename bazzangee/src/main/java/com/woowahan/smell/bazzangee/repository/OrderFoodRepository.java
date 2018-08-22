package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFoodRepository extends CrudRepository<OrderFood, Long> {
    List<OrderFood> findAllByOrderedUserOrderByOrderTimeDesc(User user);
    List<OrderFood> findAllByOrderedUser(User user);
}

