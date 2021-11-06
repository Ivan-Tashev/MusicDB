package com.example.musicdb.service.impl;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.entity.LogEntity;
import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.service.LogServiceModel;
import com.example.musicdb.repo.LogRepo;
import com.example.musicdb.service.AlbumService;
import com.example.musicdb.service.LogService;
import com.example.musicdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepo logRepo;
    private final AlbumService albumService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepo logRepo, AlbumService albumService, UserService userService, ModelMapper modelMapper) {
        this.logRepo = logRepo;
        this.albumService = albumService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(Long albumId, String action) {
        AlbumEntity albumEntity = albumService.findEntityById(albumId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        LogEntity logEntity = new LogEntity().setAlbumEntity(albumEntity)
                .setUserEntity(userEntity)
                .setAction(action)
                .setDateTime(LocalDateTime.now());

        logRepo.save(logEntity);
    }

    @Override
    public List<LogServiceModel> findAllLogs() {
        return logRepo.findAll().stream()
                .map(logEntity -> modelMapper.map(logEntity, LogServiceModel.class)
                        .setUser(logEntity.getUserEntity().getUsername())
                        .setAlbum(logEntity.getAlbumEntity().getName()))
                .collect(Collectors.toList());
    }
}
