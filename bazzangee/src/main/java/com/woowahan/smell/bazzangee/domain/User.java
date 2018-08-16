package com.woowahan.smell.bazzangee.domain;

import com.woowahan.smell.bazzangee.dto.UserLoginDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Builder
    public User(String userId, String password, String name, String phoneNumber, LocalDate birth) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.type = UserType.NORMAL;
    }

    public User(String userId, String name, UserType type) {
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public boolean matchPasswordBy(UserLoginDto userLoginDto, PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(userLoginDto.getPassword(), this.password)) {
            throw new NotMatchException("패스워드가 일치하지 않습니다.");
        }
        return true;
    }


}
