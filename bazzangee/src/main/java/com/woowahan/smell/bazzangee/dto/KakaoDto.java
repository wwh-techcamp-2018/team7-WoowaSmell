package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.domain.UserType;

public class KakaoDto {

    private String userId;
    private String password;
    private String name;
    private String imageUrl;

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "KakaoDto{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public User toUser() {
        return new User(this.userId, this.name, UserType.KAKAO);
    }
}
