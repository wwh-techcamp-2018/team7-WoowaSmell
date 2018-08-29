package com.woowahan.smell.bazzangee.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ChatMessageResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String contents;
    private String profileImgURL;
    private String imageURL;
    private LocalDateTime writtenTime;

    public LocalDateTime getWrittenTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(writtenTime.format(dateTimeFormatter), dateTimeFormatter);
    }
}