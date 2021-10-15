package com.example.musicdb.repo;

import com.example.musicdb.model.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepo extends JpaRepository<ArtistEntity, Long> {
}
