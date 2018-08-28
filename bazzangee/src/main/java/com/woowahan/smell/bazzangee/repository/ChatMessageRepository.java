package com.woowahan.smell.bazzangee.repository;

import com.woowahan.smell.bazzangee.domain.Chat;
import com.woowahan.smell.bazzangee.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    Optional<ChatMessage> findAllByChat(Chat chat);
}
