package com.aidar.tuneflow.mapper;

import com.aidar.tuneflow.dto.embedded.ChansonEmbedded;
import com.aidar.tuneflow.dto.request.ChansonRequest;
import com.aidar.tuneflow.dto.response.ChansonResponse;
import com.aidar.tuneflow.model.Chanson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChansonMapper {
    Chanson toEntity(ChansonRequest request);
    ChansonResponse toResponse(Chanson chanson);
    ChansonEmbedded toEmbedded(Chanson chanson);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audioFileId", ignore = true)
    @Mapping(target = "dateAjout", ignore = true)
    void updateEntityFromRequest(ChansonRequest request, @MappingTarget Chanson chanson);
}