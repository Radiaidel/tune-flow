package com.aidar.tuneflow.repository;


import com.aidar.tuneflow.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    Page<Album> findByTitreContaining(String titre, Pageable pageable);
    Page<Album> findByArtiste(String artiste, Pageable pageable);
    Page<Album> findByAnnee(Integer annee, Pageable pageable);
}





