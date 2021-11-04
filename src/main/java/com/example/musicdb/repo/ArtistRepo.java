package com.example.musicdb.repo;

import com.example.musicdb.model.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepo extends JpaRepository<ArtistEntity, Long> {

    @Query("SELECT a.name FROM ArtistEntity a")
    List<String> findAllArtistNames();
}
