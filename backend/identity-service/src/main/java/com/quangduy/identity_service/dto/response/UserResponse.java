package com.quangduy.identity_service.dto.response;

import com.quangduy.identity_service.constant.PredefinedRole;
import com.quangduy.identity_service.constant.TypeEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String _id;
    String username;
    String email;
    String refreshToken;
    String address;
    boolean isVerify;
    TypeEnum type;
    String name;
    String role;
    String gender;
    int age;
}
