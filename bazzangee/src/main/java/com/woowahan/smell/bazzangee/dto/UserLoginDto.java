package com.woowahan.smell.bazzangee.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginDto {
    private String userId;
    private String password;

    @Builder
    public UserLoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
