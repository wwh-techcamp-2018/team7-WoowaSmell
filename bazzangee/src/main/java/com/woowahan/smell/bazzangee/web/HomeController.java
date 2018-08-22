package com.woowahan.smell.bazzangee.web;

import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.getUserFromSession;

@Controller
public class HomeController {

    @GetMapping("/closet")
    public String closet(HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        if(loginUser == null) {
            return "/login";
        }
        model.addAttribute("loginUser", loginUser);
        return "/closet/closet";
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User loginUser = getUserFromSession(session);
        if(loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        return "/index";
    }

    @GetMapping("/join")
    public String join() {
        return "/user/join";
    }


}
