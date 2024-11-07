package com.quangduy.identity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quangduy.identity_service.constant.TypeEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    @JsonProperty("access_token")
    String accessToken;
    String refreshToken;
    UserLogin user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserLogin {
        String _id;
        String username;
        String email;
        String address;
        boolean isVerify;
        TypeEnum type;
        String name;
        String role;
        String gender;
        int age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserGetAccount {
        UserLogin user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserInsideToken {
        String _id;
        String email;
        String name;
    }
}
