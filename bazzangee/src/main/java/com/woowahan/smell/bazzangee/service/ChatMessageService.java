package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.Chat;
import com.woowahan.smell.bazzangee.domain.ChatMessage;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.request.ChatMessageRequestDto;
import com.woowahan.smell.bazzangee.dto.response.ChatMessageResponseDto;
import com.woowahan.smell.bazzangee.exception.BadRequestException;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.repository.ChatMessageRepository;
import com.woowahan.smell.bazzangee.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRepository chatRepository;

    public List<ChatMessageResponseDto> getList(Long roomId) {
        if(roomId > chatRepository.count()) throw new BadRequestException("잘못된 채팅방 요청입니다.");

        return findChatById(roomId)
                .getChatMessges()
                .stream()
                .map(ChatMessage::toChatMessageResponseDto)
                .collect(Collectors.toList());
    }

    public ChatMessageResponseDto create(ChatMessageRequestDto chatMessageRequestDto, User talker) {
        return chatMessageRepository.save(chatMessageRequestDto.toChatMessage(talker, findChatById(chatMessageRequestDto.getRoomId())))
                .toChatMessageResponseDto();
    }

    private Chat findChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new NotMatchException("일치하는 채팅방이 존재하지 않습니다."));
    }
}
