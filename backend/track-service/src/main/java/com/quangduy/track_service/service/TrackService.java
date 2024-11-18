package com.quangduy.track_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.UserResponse;
import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.entity.Track;
import com.quangduy.track_service.mapper.TrackMapper;
import com.quangduy.track_service.repository.TrackRepository;
import com.quangduy.track_service.repository.httpClient.IdentityClient;
import com.quangduy.track_service.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackService {
    TrackRepository trackRepository;
    TrackMapper trackMapper;
    IdentityClient identityClient;

    public TrackResponse create(TrackCreationRequest request) {
        Track track = this.trackMapper.toTrack(request);
        var util = SecurityUtil.getCurrentUserLogin();
        UserResponse user = this.identityClient.getUserByUsername(util.get()).getData();
        Track.Uploader uploader = Track.Uploader.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .type(user.getType())
                .build();
        track.setUploader(uploader);
        this.trackRepository.save(track);
        return this.trackMapper.toTrackResponse(track);
    }

    public ApiPagination<TrackResponse> getAllTracks(Pageable pageable) {
        log.info("Get all users");
        Page<Track> pageTrack = this.trackRepository.findAll(pageable);

        List<TrackResponse> listTrack = pageTrack.getContent().stream().map(this.trackMapper::toTrackResponse).toList();

        ApiPagination.Meta mt = new ApiPagination.Meta();

        mt.setCurrent(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageTrack.getTotalPages());
        mt.setTotal(pageTrack.getTotalElements());

        return ApiPagination.<TrackResponse>builder()
                .meta(mt)
                .result(listTrack)
                .build();
    }
}
