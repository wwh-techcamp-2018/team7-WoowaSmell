package com.woowahan.smell.bazzangee.web.message;

import com.woowahan.smell.bazzangee.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topic")
public class ChatController {
    @MessageMapping("hello")
    @SendTo("/hello")
    public ChatMessage hello(ChatMessage message) {
    return message;
}

    @MessageMapping("bye")
    @SendTo("/bye")
    public ChatMessage bye(ChatMessage message) {
        return message;
    }

    @MessageMapping("chat")
    @SendTo("/message")
    public ChatMessage chat(ChatMessage message) {
        return message;
    }
}
