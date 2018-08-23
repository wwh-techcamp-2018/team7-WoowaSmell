package com.woowahan.smell.bazzangee.dto;

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
    private String username;
    private String contents;
    private LocalDateTime writtenTime;
}
