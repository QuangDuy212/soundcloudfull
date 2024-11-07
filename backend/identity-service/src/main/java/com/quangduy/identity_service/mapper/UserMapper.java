package com.quangduy.identity_service.mapper;

import org.mapstruct.Mapper;

import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    User toUser(UserCreataionRequest request);
}
