package com.aidar.tuneflow.service;

import com.aidar.tuneflow.dto.request.ChansonRequest;
import com.aidar.tuneflow.dto.response.ChansonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ChansonService {
    Page<ChansonResponse> getAllChansons(Pageable pageable);
    Page<ChansonResponse> searchChansonsByTitle(String title, Pageable pageable);
    Page<ChansonResponse> getChansonsByAlbum(String albumId, Pageable pageable);
    ChansonResponse addChanson(ChansonRequest request, MultipartFile audioFile);
    ChansonResponse updateChanson(String id, ChansonRequest request);
    void deleteChanson(String id);
}
