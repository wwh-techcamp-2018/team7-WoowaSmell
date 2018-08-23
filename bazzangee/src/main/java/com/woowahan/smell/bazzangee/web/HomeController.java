package com.woowahan.smell.bazzangee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.getUserFromSession;

@Controller
public class HomeController {

    @GetMapping("/closet")
    public String closet(HttpSession session) {
        if(getUserFromSession(session) == null) {
            return "/login";
        }
        return "/closet/closet";
    }
}
