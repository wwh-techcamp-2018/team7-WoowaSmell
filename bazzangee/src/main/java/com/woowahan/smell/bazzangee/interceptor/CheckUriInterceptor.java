package com.woowahan.smell.bazzangee.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CheckUriInterceptor extends HandlerInterceptorAdapter {
    private static final String[] allowedUris = new String[] {"/api/reviews", "/closet", "/static"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String uriPath = request.getServletPath();

        for (String uris : allowedUris) {
            if(uris.startsWith(uriPath) || uris.equalsIgnoreCase("/")) {
                log.info("uri path : {}, pass success", uriPath);
                return true;
            }
        }
        response.sendRedirect("/errors/404");
        return false;
    }

}
