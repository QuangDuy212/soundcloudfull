package com.quangduy.identity_service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping(
            // path = "/api/v1/users"
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ApiResponse<UserResponse> create(UserCreataionRequest request) {
        return ApiResponse.<UserResponse>builder()
                .statusCode(200)
                .message("Create success")
                .data(this.userService.create(request))
                .build();
    }
}
