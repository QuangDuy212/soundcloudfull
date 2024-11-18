package com.quangduy.track_service.mapper;

import org.mapstruct.Mapper;

import com.quangduy.track_service.dto.request.TrackCreationRequest;
import com.quangduy.track_service.dto.response.TrackResponse;
import com.quangduy.track_service.entity.Track;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackResponse toTrackResponse(Track track);

    Track toTrack(TrackCreationRequest request);
}
