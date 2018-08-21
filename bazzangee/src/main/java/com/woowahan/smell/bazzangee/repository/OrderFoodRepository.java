package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.OrderFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderFoodRepository extends CrudRepository<OrderFood, Long> {
    Page<OrderFood> findAllOrderByOrderTime(Pageable pageable);
}
