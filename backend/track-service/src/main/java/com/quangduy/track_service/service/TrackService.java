package com.quangduy.track_service.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.quangduy.common_service.dto.response.ApiPagination;
import com.quangduy.common_service.dto.response.UserResponse;
import com.quangduy.track_service.dto.request.GetTrackCreatedByUser;
import com.quangduy.track_service.dto.request.GetTrackTopRequest;
import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.request.TrackSearchRequest;
import com.quangduy.track_service.dto.request.TrackUpdateRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.entity.Track;
import com.quangduy.track_service.mapper.TrackMapper;
import com.quangduy.track_service.repository.TrackRepository;
import com.quangduy.track_service.repository.httpClient.IdentityClient;
import com.quangduy.track_service.util.SecurityUtil;
import com.quangduy.track_service.util.exception.MyAppException;

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
        track.setCreatedAt(Instant.now());
        track.setUpdatedAt(Instant.now());
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

    public TrackResponse update(String trackId, TrackUpdateRequest request) {
        Optional<Track> track = this.trackRepository.findById(trackId);

        if (track.isPresent()) {
            this.trackMapper.updateTrack(track.get(), request);
            track.get().setUpdatedAt(Instant.now());
        }

        return this.trackMapper.toTrackResponse(this.trackRepository.save(track.get()));
    }

    public void delete(String trackId) throws MyAppException {
        boolean check = this.trackRepository.existsById(trackId);
        if (!check)
            throw new MyAppException("Track not existed");
        this.trackRepository.deleteById(trackId);
    }

    public TrackResponse fetchUserById(String id) throws MyAppException {
        Track track = null;
        try {
            track = this.trackRepository.findById(id)
                    .orElseThrow(() -> new MyAppException("Track don't exist"));
        } catch (MyAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.trackMapper.toTrackResponse(track);
    }

    public List<TrackResponse> fetchTrackByCategory(GetTrackTopRequest request) {
        Pageable pageable = PageRequest.of(0, request.getLimit(), Sort.by("countPlay"));

        return this.trackRepository.findByCategory(request.getCategory(), pageable)
                .stream().map(trackMapper::toTrackResponse).toList();
    }

    public ApiPagination<TrackResponse> searchTracks(TrackSearchRequest request) {
        log.info("Search track with name");
        Pageable pageable = PageRequest.of(request.getCurrent(), request.getPageSize());
        Page<Track> pageTrack = this.trackRepository.findByTitleContains(request.getTitle(), pageable);

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

    public ApiPagination<TrackResponse> getTrackCreatedByUser(GetTrackCreatedByUser request, Pageable pageable) {
        log.info("Get track created by user");
        UserResponse user = null;
        try {
            user = this.identityClient.getDetailUser(request.getId()).getData();
        } catch (Exception e) {
            // TODO: handle exception
            log.info("Has an error in identity client get detail user: " + e);
        }

        Track.Uploader uploader = this.trackMapper.toUploader(user);
        Page<Track> pageTrack = this.trackRepository.findByUploader(uploader, pageable);

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
