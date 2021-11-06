package com.example.musicdb.service;

import com.example.musicdb.model.entity.ArtistEntity;
import com.example.musicdb.model.view.ArtistViewModel;

import java.util.List;

public interface ArtistService {

    List<String> findAllArtists();

    void seedArtists();

    ArtistEntity findByName(String artist);
}


