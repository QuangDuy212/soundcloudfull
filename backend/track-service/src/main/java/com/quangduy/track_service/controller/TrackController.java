package com.quangduy.track_service.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.common_service.annotation.ApiMessage;
import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.request.TrackUpdateRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.service.TrackService;
import com.quangduy.track_service.util.exception.MyAppException;

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
    ResponseEntity<TrackResponse> create(
            @Valid @RequestBody TrackCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.trackService.create(request));
    }

    @GetMapping
    @ApiMessage("Get all tracks success")
    ResponseEntity<ApiPagination<TrackResponse>> getTracksWithPagination(Pageable pageable) {
        return ResponseEntity.ok().body(this.trackService.getAllTracks(pageable));
    }

    @GetMapping("/{trackId}")
    @ApiMessage("Get a track success")
    ResponseEntity<TrackResponse> update(
            @PathVariable("trackId") String trackId) throws MyAppException {
        return ResponseEntity.ok().body(this.trackService.fetchUserById(trackId));
    }

    @PutMapping("/{trackId}")
    @ApiMessage("Update a track success")
    ResponseEntity<TrackResponse> update(
            @PathVariable("trackId") String trackId,
            @RequestBody TrackUpdateRequest request) {
        return ResponseEntity.ok().body(this.trackService.update(trackId, request));
    }

    @DeleteMapping("/{trackId}")
    @ApiMessage("Delete a track success")
    ResponseEntity<Void> delete(
            @PathVariable("trackId") String trackId) throws MyAppException {
        this.trackService.delete(trackId);
        return ResponseEntity.ok(null);
    }
}
