package com.quangduy.identity_service.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.annotation.ApiMessage;
import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.common_service.dto.response.UserResponse;
import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
import com.quangduy.identity_service.service.UserService;
import com.quangduy.identity_service.util.exception.ConstantException;
import com.quangduy.identity_service.util.exception.MyAppException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping
    @ApiMessage("Create user success")
    ResponseEntity<UserResponse> create(@RequestBody UserCreataionRequest request) throws MyAppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.create(request));
    }

    @GetMapping("/all")
    @ApiMessage("Get all users success")
    ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @GetMapping()
    @ApiMessage("Get all users success")
    ResponseEntity<ApiPagination<UserResponse>> getUsersWithPagination(Pageable pageable) {
        return ResponseEntity.ok().body(this.userService.getUsers(pageable));
    }

    @GetMapping("/{userId}")
    @ApiMessage("Get detail a user success")
    ResponseEntity<UserResponse> getDetailUser(@PathVariable("userId") String userId) throws ConstantException {
        return ResponseEntity.ok().body(this.userService.fetchUserById(userId));
    }

    @GetMapping("/name/{username}")
    @ApiMessage("Get detail a user success")
    ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) throws MyAppException {
        return ResponseEntity.ok().body(this.userService.fetchUserByUsername(username));
    }

    @DeleteMapping("/{userId}")
    @ApiMessage("Delete a user success")
    ResponseEntity<Void> delete(@PathVariable("userId") String userId) throws ConstantException {
        this.userService.delete(userId);
        return ResponseEntity.ok(null);
    }

    @PutMapping()
    @ApiMessage("Update a user success")
    ResponseEntity<UserResponse> updateUser(
            @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok().body(this.userService.updateUser(request));
    }
}
