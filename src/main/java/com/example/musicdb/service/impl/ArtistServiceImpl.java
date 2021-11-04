package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.ArtistEntity;
import com.example.musicdb.repo.ArtistRepo;
import com.example.musicdb.service.ArtistService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final Resource artistsFile;
    private final Gson gson;
    private final ArtistRepo artistRepo;
    private final ModelMapper modelMapper;

    public ArtistServiceImpl(@Value("classpath:init/artists.json") Resource artistsFile,
                             Gson gson, ArtistRepo artistRepo, ModelMapper modelMapper) { // read value from file
        this.artistsFile = artistsFile;
        this.gson = gson;
        this.artistRepo = artistRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> findAllArtists() {
        return artistRepo.findAllArtistNames();
    }

    @Override
    public void seedArtists() {
        if (artistRepo.count() == 0) {
            try {
                ArtistEntity[] artistEntities = gson.fromJson(
                        Files.readString(Path.of(artistsFile.getURI())), ArtistEntity[].class);

                artistRepo.saveAll(Arrays.asList(artistEntities));
            } catch (IOException e) {
                throw new IllegalStateException("Can't seed artists from artists.json file.");
            }
        }
    }
}
