package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    @Column
    @Lob
    private String contents;
    @Column
    private LocalDateTime writtenTime;
}
