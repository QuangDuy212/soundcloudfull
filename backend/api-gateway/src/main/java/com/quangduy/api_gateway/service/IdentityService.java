package com.quangduy.api_gateway.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import com.quangduy.api_gateway.dto.request.IntrospectRequest;
import com.quangduy.api_gateway.dto.response.IntrospectResponse;
import com.quangduy.api_gateway.repository.IdentityClient;
import com.quangduy.common_service.dto.response.ApiResponse;

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return identityClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
