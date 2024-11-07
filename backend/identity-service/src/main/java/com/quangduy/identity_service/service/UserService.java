package com.quangduy.identity_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quangduy.identity_service.constant.PredefinedRole;
import com.quangduy.identity_service.constant.TypeEnum;
import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.entity.User;
import com.quangduy.identity_service.mapper.UserMapper;
import com.quangduy.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse create(UserCreataionRequest request) {
        User user = this.userMapper.toUser(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setType(TypeEnum.SYSTEM);
        user.setVerify(false);
        if (request.getRole().equals(null)) {
            user.setRole(PredefinedRole.USER_ROLE);
        }
        user = this.userRepository.save(user);
        UserResponse response = this.userMapper.toUserResponse(user);
        return response;
    }
}
