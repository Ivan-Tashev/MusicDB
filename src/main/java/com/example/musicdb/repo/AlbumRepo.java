package com.example.musicdb.repo;

import com.example.musicdb.model.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepo extends JpaRepository<AlbumEntity, Long> {
}
