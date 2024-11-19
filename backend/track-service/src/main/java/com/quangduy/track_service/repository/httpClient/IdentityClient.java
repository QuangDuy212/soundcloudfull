package com.quangduy.track_service.repository.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.common_service.dto.response.UserResponse;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}")
public interface IdentityClient {
    @GetMapping(value = "/users/name/{username}")
    ApiResponse<UserResponse> getUserByUsername(@PathVariable String username);

    @GetMapping(value = "/users/{userId}")
    ApiResponse<UserResponse> getDetailUser(@PathVariable String userId);
}
