package com.aidar.tuneflow.repository;

import com.aidar.tuneflow.model.Chanson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChansonRepository extends MongoRepository<Chanson, String> {
    Page<Chanson> findByTitreContaining(String titre, Pageable pageable);
    Page<Chanson> findByAlbumId(String albumId, Pageable pageable);
}