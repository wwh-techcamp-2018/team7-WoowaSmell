package com.woowahan.smell.bazzangee.web.message;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.request.ChatMessageRequestDto;
import com.woowahan.smell.bazzangee.dto.request.InitialChatInfoRequestDto;
import com.woowahan.smell.bazzangee.dto.response.ChatMessageResponseDto;
import com.woowahan.smell.bazzangee.dto.response.GoodResponseDto;
import com.woowahan.smell.bazzangee.dto.response.InitialChatInfoResponseDto;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.ChatMessageService;
import com.woowahan.smell.bazzangee.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("info")
    @SendToUser("/queue/info")
    public InitialChatInfoResponseDto info(InitialChatInfoRequestDto initialChatInfoRequestDto,
                                           SimpMessageHeaderAccessor messageHeaderAccessor) {
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        return new InitialChatInfoResponseDto(
                chatMessageService.getList(initialChatInfoRequestDto.getRoomId()),
                talker == null ? null : talker.toLimitInfoUser()
        );
    }

    @MessageMapping("me")
    @SendToUser("/queue/me")
    public User info(SimpMessageHeaderAccessor messageHeaderAccessor) {
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        if(talker == null)
            throw new UnAuthenticationException("로그인한 사용자만 이용 가능합니다.");
        return talker.toLimitInfoUser();
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

    @MessageMapping("good")
    @SendToUser("/queue/good")
    public GoodResponseDto good(@Payload Long goodId, SimpMessageHeaderAccessor messageHeaderAccessor) {
        User talker = getSessionUser(messageHeaderAccessor.getSessionAttributes());
        if (talker == null) return GoodResponseDto.ofString("로그인한 사용자만 이용 가능합니다.");

        GoodResponseDto goodResponseDto = reviewService.updateGood(goodId, talker);
        log.info("goodResponseDto : {}", goodResponseDto);
        if (goodResponseDto.getMessage().equals("LIKE")) {
            simpMessagingTemplate.convertAndSend(String.format("/queue/%s/good", goodResponseDto.getWriterId()), goodResponseDto);
        }
        return goodResponseDto;
    }

    private User getSessionUser(Map<String, Object> sessionAttributes) {
        if (sessionAttributes == null || sessionAttributes.get(SESSION) == null) return null;
        return getUserFromSession((HttpSession) sessionAttributes.get(SESSION));
    }

}
