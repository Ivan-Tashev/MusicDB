package com.example.musicdb.web;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.repo.AlbumRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumRestController {
    private final AlbumRepo albumRepo;
    private final ModelMapper modelMapper;

    public AlbumRestController(AlbumRepo albumRepo, ModelMapper modelMapper) {
        this.albumRepo = albumRepo;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<AlbumEntity>> findAll() {
        return ResponseEntity.ok().body(albumRepo.findAll());
    }
}
