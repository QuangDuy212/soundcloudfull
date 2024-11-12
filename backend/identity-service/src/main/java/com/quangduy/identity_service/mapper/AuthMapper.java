package com.quangduy.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quangduy.identity_service.dto.request.RegisterRequest;
import com.quangduy.identity_service.dto.response.LoginResponse;
import com.quangduy.identity_service.dto.response.RegisterResponse;
import com.quangduy.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    RegisterResponse toRegisterResponse(User user);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    User toUser(RegisterRequest request);

    LoginResponse.UserLogin toUserLogin(User user);
}
