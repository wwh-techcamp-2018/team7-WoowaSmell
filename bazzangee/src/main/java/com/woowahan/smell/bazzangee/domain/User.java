package com.woowahan.smell.bazzangee.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String phoneNumber;
    @Column
    private LocalDate birth;
    @Column
    private LocalDateTime joinDate;
}
