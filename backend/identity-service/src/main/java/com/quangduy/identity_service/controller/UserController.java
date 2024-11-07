package com.quangduy.identity_service.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.identity_service.dto.request.UserCreataionRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
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
    @ResponseBody
    ApiResponse<UserResponse> create(UserCreataionRequest request) {
        return ApiResponse.<UserResponse>builder()
                .statusCode(200)
                .message("Create success")
                .data(this.userService.create(request))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .statusCode(200)
                .message("Get all users success")
                .data(this.userService.getUsers())
                .build();
    }

    @GetMapping()
    ApiResponse<ApiPagination<UserResponse>> getUsersWithPagination(Pageable pageable) {
        return ApiResponse.<ApiPagination<UserResponse>>builder()
                .statusCode(200)
                .message("Get all users by pagination success")
                .data(this.userService.getUsers(pageable))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable("userId") String userId) {
        return ApiResponse.<String>builder()
                .statusCode(200)
                .message("Delete a user success")
                .data(this.userService.delete(userId))
                .build();
    }

    @PutMapping()
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .statusCode(200)
                .message("Update a user success")
                .data(this.userService.updateUser(request))
                .build();
    }
}
