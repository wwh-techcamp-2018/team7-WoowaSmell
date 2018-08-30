package com.woowahan.smell.bazzangee.dto.request;

import com.woowahan.smell.bazzangee.domain.contents.Chat;
import com.woowahan.smell.bazzangee.domain.contents.ChatMessage;
import com.woowahan.smell.bazzangee.domain.User;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatMessageRequestDto {
    private Long roomId;
    private String message;
    private String imageURL;

    public ChatMessage toChatMessage(User talker, Chat chat) {
        return new ChatMessage(talker, chat, message, imageURL);
    }

}
