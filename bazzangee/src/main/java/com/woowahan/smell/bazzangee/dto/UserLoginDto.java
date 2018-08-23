package com.woowahan.smell.bazzangee.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginDto {
    private String userId;
    private String password;
    private String imageUrl;

    @Builder
    public UserLoginDto(String userId, String password, String imageUrl) {
        this.userId = userId;
        this.password = password;
        this.imageUrl = imageUrl;
    }
}
