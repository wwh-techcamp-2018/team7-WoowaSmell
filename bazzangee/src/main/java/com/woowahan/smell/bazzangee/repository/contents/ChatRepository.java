package com.woowahan.smell.bazzangee.repository.contents;

import com.woowahan.smell.bazzangee.domain.contents.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    Optional<Chat> findAllByName(String name);
}
