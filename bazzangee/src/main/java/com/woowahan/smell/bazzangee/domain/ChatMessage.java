package com.woowahan.smell.bazzangee.domain;

import com.woowahan.smell.bazzangee.dto.ChatMessageResponseDto;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_chat_message_chat"))
    private Chat chat;
    @Column
    @Lob
    private String contents;
    @Column
    private LocalDateTime writtenTime;

    @Column
    private String imageURL;

    public ChatMessage(User user, Chat chat, String contents, String imageURL) {
        this.user = user;
        this.chat = chat;
        this.contents = contents;
        this.writtenTime = LocalDateTime.now();
        this.imageURL = imageURL;
    }

    public ChatMessageResponseDto toChatMessageResponseDto() {
        return new ChatMessageResponseDto(
                id,
                user.getId(),
                user.getName(),
                contents,
                user.getImageUrl(),
                imageURL,
                writtenTime
        );
    }
}
