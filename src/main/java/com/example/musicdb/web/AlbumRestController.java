package com.example.musicdb.web;

import com.example.musicdb.model.view.AlbumViewModel;
import com.example.musicdb.repo.AlbumRepo;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AlbumViewModel> findAll() {
        return albumRepo.findAll().stream()
                .map(albumEntity -> modelMapper.map(albumEntity, AlbumViewModel.class))
                .collect(Collectors.toList());
    }
}
