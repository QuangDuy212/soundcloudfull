package com.quangduy.track_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quangduy.track_service.entity.Track;

public interface TrackRepository extends MongoRepository<Track, String> {
    Page<Track> findAll(Pageable pageable);

    List<Track> findByCategory(String category, Pageable pageable);

    Page<Track> findByTitleContains(String title, Pageable pageable);

    Page<Track> findByUploader(Track.Uploader uploader, Pageable pageable);
}
