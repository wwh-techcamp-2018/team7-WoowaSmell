package com.woowahan.smell.bazzangee.repository.contents;

import com.woowahan.smell.bazzangee.domain.contents.Chat;
import com.woowahan.smell.bazzangee.domain.contents.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    Optional<ChatMessage> findAllByChat(Chat chat);
}
