package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InitialChatInfoResponseDto {
    Iterable<ChatMessageResponseDto> chatMessageResponseDtos;
    User loginUser;
}
