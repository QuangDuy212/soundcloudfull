package com.quangduy.identity_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.identity_service.dto.request.IntrospectRequest;
import com.quangduy.identity_service.dto.request.LoginRequest;
import com.quangduy.identity_service.dto.request.RegisterRequest;
import com.quangduy.identity_service.dto.response.IntrospectResponse;
import com.quangduy.identity_service.dto.response.LoginResponse;
import com.quangduy.identity_service.dto.response.RegisterResponse;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.service.AuthService;
import com.quangduy.identity_service.util.annotation.ApiMessage;
import com.quangduy.identity_service.util.exception.MyAppException;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    @ApiMessage("Login success")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return this.authService.login(request);
    }

    @PostMapping("/register")
    @ApiMessage("Register success")
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) throws MyAppException {
        return this.authService.register(request);
    }

    @GetMapping("/account")
    @ApiMessage("Get account success")
    ResponseEntity<UserResponse> getAccount() {
        return this.authService.getAccount();
    }

    @PostMapping("/logout")
    @ApiMessage("Logout success")
    ResponseEntity<Void> logout() throws MyAppException {
        return this.authService.logout();
    }

    @PostMapping("/refresh")
    @ApiMessage("Refresh Token success")
    ResponseEntity<LoginResponse> refreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "duy") String refresh_token) throws MyAppException {
        return this.authService.refreshToken(refresh_token);
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = this.authService.introspect(request);
        return result;
    }
}
