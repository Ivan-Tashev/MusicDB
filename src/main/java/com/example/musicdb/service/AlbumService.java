package com.example.musicdb.service;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.service.AlbumServiceModel;
import com.example.musicdb.model.view.AlbumViewModel;

public interface AlbumService {

    void createAlbum(AlbumServiceModel albumServiceModel);

    AlbumViewModel findById(Long id);

    AlbumEntity findEntityById(Long albumId);
}
