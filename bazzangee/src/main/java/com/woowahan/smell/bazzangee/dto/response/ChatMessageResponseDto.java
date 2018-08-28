package com.woowahan.smell.bazzangee.dto.response;

import lombok.*;
import org.springframework.messaging.Message;

import java.time.LocalDateTime;

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
    private LocalDateTime writtenTime;
}
