package com.quangduy.track_service.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.annotation.ApiMessage;
import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.ApiResponse;
import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.service.TrackService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackController {
    TrackService trackService;

    @PostMapping("/create")
    @ApiMessage("Create tracks success")
    ResponseEntity<TrackResponse> create(@Valid @RequestBody TrackCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.trackService.create(request));
    }

    @GetMapping
    @ApiMessage("Get all tracks success")
    ResponseEntity<ApiPagination<TrackResponse>> getTracksWithPagination(Pageable pageable) {
        return ResponseEntity.ok().body(this.trackService.getAllTracks(pageable));
    }
}
