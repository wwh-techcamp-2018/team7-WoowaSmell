package com.woowahan.smell.bazzangee.web.message;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.ChatMessageRequestDto;
import com.woowahan.smell.bazzangee.dto.ChatMessageResponseDto;
import com.woowahan.smell.bazzangee.dto.InitialChatInfoRequestDto;
import com.woowahan.smell.bazzangee.dto.InitialChatInfoResponseDto;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.ChatMessageService;
import com.woowahan.smell.bazzangee.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.SESSION;
import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.getUserFromSession;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("info")
    @SendToUser("/queue/info")
    public InitialChatInfoResponseDto info(InitialChatInfoRequestDto initialChatInfoRequestDto, SimpMessageHeaderAccessor messageHeaderAccessor) {
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        return new InitialChatInfoResponseDto(
                chatMessageService.getList(initialChatInfoRequestDto.getRoomId()),
                talker == null ? null : talker.toLimitInfoUser()
        );
    }

    @MessageMapping("chat")
    @SendTo("/topic/message")
    public ChatMessageResponseDto chat(ChatMessageRequestDto chatMessageRequestDto, SimpMessageHeaderAccessor messageHeaderAccessor) {
        log.info("params : {}", chatMessageRequestDto);
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        if (talker == null) throw new UnAuthenticationException("로그인한 사용자만 채팅에 참여할 수 있습니다.");

        return chatMessageService.create(
                chatMessageRequestDto,
                talker.toLimitInfoUser()
        );
    }

    @MessageMapping("/img")
    @SendTo("/topic/message")
    public ChatMessageResponseDto showImage(ChatMessageRequestDto chatMessageRequestDto, SimpMessageHeaderAccessor messageHeaderAccessor) throws Exception {
        log.info("chatMessageRequestDto : {}", chatMessageRequestDto);
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        if (talker == null) throw new UnAuthenticationException("로그인한 사용자만 채팅에 참여할 수 있습니다.");

        return chatMessageService.create(
                chatMessageRequestDto,
                talker.toLimitInfoUser()
        );

    }

    @MessageMapping("bye")
    @SendTo("/topic/bye")
    public ChatMessageRequestDto bye(ChatMessageRequestDto message) {
        return message;
    }

    private User getSessionUser(Map<String, Object> sessionAttributes) {
        if (sessionAttributes == null || sessionAttributes.get(SESSION) == null) return null;
        return getUserFromSession((HttpSession) sessionAttributes.get(SESSION));
    }

}
