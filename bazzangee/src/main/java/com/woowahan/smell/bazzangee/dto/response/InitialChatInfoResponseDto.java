package com.woowahan.smell.bazzangee.dto.response;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.response.ChatMessageResponseDto;
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
