package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.dto.ChatMessageResponseDto;
import com.woowahan.smell.bazzangee.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ApiChatController {
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/{roomId}")
    public ResponseEntity<Iterable<ChatMessageResponseDto>> getListByChatName(@PathVariable Long roomId) {
        return ResponseEntity.status(HttpStatus.OK).body(chatMessageService.getList(roomId));
    }
}
