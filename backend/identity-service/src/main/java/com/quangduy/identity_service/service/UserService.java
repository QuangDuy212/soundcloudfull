package com.quangduy.identity_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.identity_service.constant.PredefinedRole;
import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.entity.User;
import com.quangduy.identity_service.mapper.UserMapper;
import com.quangduy.identity_service.repository.UserRepository;
import com.quangduy.identity_service.util.exception.ConstantException;
import com.quangduy.identity_service.util.exception.ErrorCode;
import com.quangduy.identity_service.util.exception.MyAppException;

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

    public UserResponse create(UserCreataionRequest request) throws MyAppException {
        log.info("Create a new user");

        boolean isExistsEmail = this.isEmailExist(request.getEmail());
        if (isExistsEmail) {
            throw new MyAppException("Email đã tồn tại, vui lòng nhập lại!");
        }

        boolean isExistUsername = this.isUsernameExist(request.getUsername());
        if (isExistUsername) {
            throw new MyAppException("Username đã tồn tại, vui lòng nhập lại!");
        }
        User user = this.userMapper.toUser(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setType("SYSTEM");
        user.setVerify(false);
        if (request.getRole().equals(null)) {
            user.setRole(PredefinedRole.USER_ROLE);
        }
        user = this.userRepository.save(user);
        UserResponse response = this.userMapper.toUserResponse(user);
        return response;
    }

    public List<UserResponse> getUsers() {
        log.info("Get all users");
        return this.userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public ApiPagination<UserResponse> getUsers(Pageable pageable) {
        log.info("Get all users");
        Page<User> pageUser = this.userRepository.findAll(pageable);

        List<UserResponse> listUser = pageUser.getContent().stream().map(userMapper::toUserResponse).toList();

        ApiPagination.Meta mt = new ApiPagination.Meta();

        mt.setCurrent(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        return ApiPagination.<UserResponse>builder()
                .meta(mt)
                .result(listUser)
                .build();
    }

    public UserResponse fetchUserById(String id) throws ConstantException {
        User user = new User();
        try {
            user = this.userRepository.findById(id)
                    .orElseThrow(() -> new ConstantException(ErrorCode.USER_NOT_EXISTED));
        } catch (ConstantException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.userMapper.toUserResponse(user);
    }

    public void delete(String userId) throws ConstantException {
        boolean check = this.userRepository.existsById(userId);
        if (!check)
            throw new ConstantException(ErrorCode.USER_NOT_EXISTED);
        this.userRepository.deleteById(userId);
    }

    public UserResponse updateUser(UserUpdateRequest request) {
        Optional<User> user = this.userRepository.findById(request.getId());

        if (user.isPresent()) {
            if (request.getAge() == 0) {
                request.setAge(user.get().getAge());
            }
            this.userMapper.updateUser(user.get(), request);
        }

        return userMapper.toUserResponse(userRepository.save(user.get()));
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void updateUserToken(String token, String username) {
        User currentUser = this.handleGetUserByUsername(username);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean isUsernameExist(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public void handleLogout(User user) {
        user.setRefreshToken(null);
        this.userRepository.save(user);
    }

    public User getUserByRefreshTokenAndUsername(String token, String username) {
        return this.userRepository.findByRefreshTokenAndUsername(token, username);
    }
}
