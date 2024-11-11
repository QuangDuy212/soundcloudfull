package com.quangduy.identity_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quangduy.identity_service.dto.request.LoginRequest;
import com.quangduy.identity_service.dto.response.LoginResponse;
import com.quangduy.identity_service.entity.User;
import com.quangduy.identity_service.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService {
        AuthenticationManagerBuilder authenticationManagerBuilder;
        SecurityUtil securityUtil;
        UserService userService;
        PasswordEncoder passwordEncoder;

        @Value("${quangduy.jwt.refresh-token-validity-in-seconds}")
        @NonFinal
        long refreshTokenExpiration;

        public ResponseEntity<LoginResponse> login(LoginRequest request) {
                // Nạp input gồm username/password vào Security
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword());

                // xác thực người dùng => cần viết hàm loadUserByUsername
                Authentication authentication = authenticationManagerBuilder.getObject()
                                .authenticate(authenticationToken);

                // save info auth into security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // return api
                LoginResponse res = new LoginResponse();
                User currentUserDB = this.userService.handleGetUserByUsername(request.getUsername());
                if (currentUserDB != null) {
                        res.setUser(LoginResponse.UserLogin.builder()
                                        .id(currentUserDB.getId())
                                        .username(currentUserDB.getUsername())
                                        .email(currentUserDB.getEmail())
                                        .address(currentUserDB.getAddress())
                                        .age(currentUserDB.getAge())
                                        .gender(currentUserDB.getGender())
                                        .isVerify(currentUserDB.isVerify())
                                        .type(currentUserDB.getType())
                                        .name(currentUserDB.getName())
                                        .role(currentUserDB.getRole())
                                        .build());
                }
                // create a token
                String access_token = this.securityUtil.createAccessToken(authentication.getName(), res);
                res.setAccessToken(access_token);

                // create refesh token
                String refresh_token = this.securityUtil.createRefreshToken(request.getUsername(), res);

                this.userService.updateUserToken(refresh_token, request.getUsername());

                // set cookies
                ResponseCookie resCookies = ResponseCookie
                                .from("refresh_token", refresh_token)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(refreshTokenExpiration)
                                .build();
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                                .body(res);
        }
}
