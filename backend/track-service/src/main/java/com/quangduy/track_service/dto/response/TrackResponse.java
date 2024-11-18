package com.quangduy.track_service.dto.response;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonPropertyOrder(alphabetic = true)
public class TrackResponse {
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
