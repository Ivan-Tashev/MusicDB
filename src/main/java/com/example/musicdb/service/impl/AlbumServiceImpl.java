package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.entity.ArtistEntity;
import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.service.AlbumServiceModel;
import com.example.musicdb.model.view.AlbumViewModel;
import com.example.musicdb.repo.AlbumRepo;
import com.example.musicdb.repo.UserRepo;
import com.example.musicdb.service.AlbumService;
import com.example.musicdb.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepo albumRepo;
    private final ArtistService artistService;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public AlbumServiceImpl(AlbumRepo albumRepo, ArtistService artistService, UserRepo userRepo, ModelMapper modelMapper) {
        this.albumRepo = albumRepo;
        this.artistService = artistService;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAlbum(AlbumServiceModel albumServiceModel) {
        AlbumEntity albumEntity = modelMapper.map(albumServiceModel, AlbumEntity.class);
        UserEntity creatorEntity = userRepo.findByUsername(albumServiceModel.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("Creator " + albumServiceModel.getUserName() + " could not be found."));
        albumEntity.setUserEntity(creatorEntity);

        ArtistEntity artistEntity = artistService.findByName(albumServiceModel.getArtist());
        albumEntity.setArtistEntity(artistEntity);

        albumRepo.save(albumEntity);
    }

    @Override
    public AlbumViewModel findById(Long id) {
        return albumRepo.findById(id).map(albumEntity -> modelMapper.map(albumEntity, AlbumViewModel.class)
                        .setArtist(artistService.findByName(albumEntity.getArtistEntity().getName()).getName()))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public AlbumEntity findEntityById(Long albumId) {
        return albumRepo.findById(albumId)
                .orElseThrow(IllegalArgumentException::new);
    }


}
