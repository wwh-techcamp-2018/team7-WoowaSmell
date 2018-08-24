package com.woowahan.smell.bazzangee.web;

import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.OrderFoodService;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/orderfoods")
public class OrderFoodController {
    @Autowired
    private OrderFoodService orderFoodService;

    @GetMapping("")
    public String list(Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 이용 가능합니다.");
        model.addAttribute("orderFoods", orderFoodService.getList(HttpSessionUtils.getUserFromSession(session)));
        return "/closet/closet";
    }
}
