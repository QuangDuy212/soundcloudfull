package com.quangduy.track_service.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(value = "tracks")
public class Track {
    @MongoId
    @JsonProperty("_id")
    String id;
    String title;
    String description;
    String category;
    String imgUrl;
    String trackUrl;
    int countLike;
    int countPlay;
    Uploader uploader;
    boolean isDeleted;
    Instant createdAt;
    Instant updatedAt;

    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Uploader {
        @JsonProperty("_id")
        String id;
        String email;
        String name;
        String role;
        String type;
    }
}
