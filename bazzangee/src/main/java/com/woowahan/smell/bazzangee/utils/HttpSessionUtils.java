package com.woowahan.smell.bazzangee.utils;

import com.woowahan.smell.bazzangee.domain.User;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginedUser";
    public static final String SESSION = "session";

    public static void setUserInSession(HttpSession session, User loginUser) {
        session.setAttribute(USER_SESSION_KEY, loginUser);
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    }

    public static User getUserFromSession(NativeWebRequest webRequest) {
        if (!isLoginUser(webRequest)) {
            return new User();
        }
        return (User) webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    private static boolean isLoginUser(NativeWebRequest webRequest) {
        Object loginedUser = webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
        return loginedUser != null;
    }

    public static void removeUserInSession(HttpSession session){
        session.removeAttribute(USER_SESSION_KEY);
    }
}


