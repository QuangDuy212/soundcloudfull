package com.quangduy.track_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quangduy.track_service.entity.Track;

public interface TrackRepository extends MongoRepository<Track, String> {
    Page<Track> findAll(Pageable pageable);
}
