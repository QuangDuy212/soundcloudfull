package com.quangduy.track_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quangduy.common_service.dto.response.UserResponse;
import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.request.TrackUpdateRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.entity.Track;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackResponse toTrackResponse(Track track);

    Track toTrack(TrackCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTrack(@MappingTarget Track track, TrackUpdateRequest request);

    Track.Uploader toUploader(UserResponse request);
}
