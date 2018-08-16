package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserJoinDto {
    @Pattern(regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$", message = "아이디 형식에 맞지 않습니다.")
    private String userId;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "8-16자리 영문,숫자,특수문자로 조합되지 않았습니다.")
    private String password;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "8-16자리 영문,숫자,특수문자로 조합되지 않았습니다.")
    private String rePassword;
    @Pattern(regexp = "^[가-힝]{2,}$", message = "이름은 한글만 가능합니다.")
    @Size(min = 2, max = 16)
    private String name;
    @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$", message = "휴대 전화 번호 형식에 맞지 않습니다.")
    private String phoneNumber;
    private LocalDate birth;

    public User toUser(String encodedPassword) throws NotMatchException {
        if (!matchPassword()) throw new NotMatchException("비밀번호가 일치하지 않습니다.");
        return new User(userId, encodedPassword, name, phoneNumber, birth);
    }

    private boolean matchPassword() {
        return rePassword.equals(password);
    }
}
