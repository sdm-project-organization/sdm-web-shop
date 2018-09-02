package com.example.springbootshop.user.model.body;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupBody {

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp="^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$") // == 이메일 형식 문자열 ==
    private String userId;

    // == Password Pattern ==
    // 1. 영문(대소문자 구분), 숫자, 특수문자 조합
    // 2. 9~12자리 사이 문자
    // 3. 같은 문자 4개 이상 사용 불가
    // 4. 비밀번호에 ID 포함 불가
    // 5. 공백문자 사용 불가
    // "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,100}$"

    @NotNull
    @Size(min = 8, max = 100)
    @Pattern(regexp="^(?=.*\\d)(?=.*[a-zA-Z]).{8,100}$") // == 영문과 숫자를 조합한 8자리 이상의 문자열 ==
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
