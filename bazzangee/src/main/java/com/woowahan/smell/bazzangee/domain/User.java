package com.woowahan.smell.bazzangee.domain;

import com.woowahan.smell.bazzangee.dto.UserLoginDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
@ToString
public class User extends BaseTimeEntity {
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
    @Column
    private String imageUrl;

    @Builder
    public User(String userId, String password, String name, String phoneNumber, LocalDate birth) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.type = UserType.NORMAL;
    }

    public User(String userId, String password, String name, String imageUrl, UserType type) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public boolean matchPasswordBy(UserLoginDto userLoginDto, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(userLoginDto.getPassword(), this.password)) {
            throw new NotMatchException("패스워드가 일치하지 않습니다.");
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password);
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
