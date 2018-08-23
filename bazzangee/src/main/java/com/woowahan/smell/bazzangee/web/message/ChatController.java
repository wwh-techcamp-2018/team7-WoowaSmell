package com.woowahan.smell.bazzangee.web.message;

import com.woowahan.smell.bazzangee.dto.ChatMessageRequestDto;
import com.woowahan.smell.bazzangee.dto.ChatMessageResponseDto;
import com.woowahan.smell.bazzangee.service.ChatMessageService;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("chat")
    @SendTo("/topic/message")
    public ChatMessageResponseDto chat(ChatMessageRequestDto chatMessageRequestDto, SimpMessageHeaderAccessor messageHeaderAccessor) {
//        Map<String, Object> sessionAttributes = messageHeaderAccessor.getSessionAttributes(event.getMessage().getHeaders()).get("HTTP.SESSION.ID");
//        log.debug("chat : {}", sessionAttributes);
        ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto();
        chatMessageResponseDto.setContents(chatMessageRequestDto.getMessage());
        chatMessageResponseDto.setWrittenTime(LocalDateTime.now());
        chatMessageResponseDto.setUsername("자바지기");
        return chatMessageResponseDto;
    }

    @MessageMapping("bye")
    @SendTo("/topic/bye")
    public ChatMessageRequestDto bye(ChatMessageRequestDto message) {
        return message;
    }
}
