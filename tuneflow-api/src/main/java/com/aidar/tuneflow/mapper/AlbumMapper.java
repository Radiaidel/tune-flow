package com.aidar.tuneflow.mapper;


import com.aidar.tuneflow.dto.request.AlbumRequest;
import com.aidar.tuneflow.dto.response.AlbumResponse;
import com.aidar.tuneflow.model.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ChansonMapper.class})
public interface AlbumMapper {
    Album toEntity(AlbumRequest request);
    AlbumResponse toResponse(Album album);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(AlbumRequest request, @MappingTarget Album album);
}

