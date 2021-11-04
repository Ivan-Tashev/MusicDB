package com.example.musicdb.service;

import com.example.musicdb.model.view.ArtistViewModel;

import java.util.List;

public interface ArtistService {

    List<ArtistViewModel> findAllArtists();

    void seedArtists();

}


