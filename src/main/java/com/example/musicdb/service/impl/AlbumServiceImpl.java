package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.service.AlbumServiceModel;
import com.example.musicdb.repo.AlbumRepo;
import com.example.musicdb.repo.UserRepo;
import com.example.musicdb.service.AlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepo albumRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public AlbumServiceImpl(AlbumRepo albumRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.albumRepo = albumRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAlbum(AlbumServiceModel albumServiceModel) {
        AlbumEntity albumEntity = modelMapper.map(albumServiceModel, AlbumEntity.class);
        UserEntity creatorEntity = userRepo.findByUsername(albumServiceModel.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("Creator could not be found."));
        albumEntity.setUserEntity(creatorEntity);
        albumRepo.save(albumEntity);
    }


}
