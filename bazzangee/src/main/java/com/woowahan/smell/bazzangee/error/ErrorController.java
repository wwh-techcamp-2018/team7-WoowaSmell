package com.woowahan.smell.bazzangee.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping("/400")
    public String error400Page() {
        return "/error/error400";
    }

    @GetMapping("/404")
    public String error404Page() {
        return "/error/error404";
    }

    @GetMapping("/500")
    public String error500Page() {
        return "/error/error500";
    }
}
