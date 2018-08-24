package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.Chat;
import com.woowahan.smell.bazzangee.domain.ChatMessage;
import com.woowahan.smell.bazzangee.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessageRequestDto {
    private String message;

    public ChatMessage toChatMessage(User talker, Chat chat) {
        return new ChatMessage(talker, chat, message);
    }
}
