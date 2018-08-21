package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.OrderFood;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.repository.OrderFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFoodService {
    @Autowired
    private OrderFoodRepository orderFoodRepository;

    public List<OrderFood> getList(User loginUser) {
        return orderFoodRepository.findByOrderedUser(loginUser)
                .orElseThrow(() -> new UnAuthenticationException("주문 내역이 없습니다."));
    }
}
