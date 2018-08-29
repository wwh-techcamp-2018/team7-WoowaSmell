package com.woowahan.smell.bazzangee.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
public enum ChatRoomName {
    GENERAL("/smell-general"),
    CHICKEN("/smell-chicken"),
    PIZZA("/smell-pizza"),
    KOREAN_FOOD("/smell-korean-food"),
    CHINESE_FOOD("/smell-chinese-food"),
    JAPANESE_FOOD("/smell-jokbal-and-bosam"),
    WESTERN_FOOD("/smell-western-food"),
    SNACK_BAR("/smell-night-food");

    private String roomName;

    ChatRoomName(String name) {
        this.roomName = name;
    }

    public static boolean isValidChatRoomName(String value) {
        return Arrays.stream(ChatRoomName.values())
                .anyMatch((chatRoomName) -> chatRoomName.name()
                .equals(value));
    }
}
