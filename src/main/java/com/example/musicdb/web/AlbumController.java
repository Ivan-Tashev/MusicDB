package com.example.musicdb.web;

import com.example.musicdb.model.binding.AlbumBindModel;
import com.example.musicdb.model.service.AlbumServiceModel;
import com.example.musicdb.service.AlbumService;
import com.example.musicdb.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final ModelMapper modelMapper;

    public AlbumController(AlbumService albumService, ArtistService artistService, ModelMapper modelMapper) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("albumBindModel")
    public AlbumBindModel albumBindModel() {
        return new AlbumBindModel();
    }

    @GetMapping("/add")
    public String getAddAlbumPage(Model model) {
        model.addAttribute("artists", artistService.findAllArtists());
        return "add-album";
    }

    @PostMapping("/add")
    public String addAlbum(@Valid AlbumBindModel albumBindModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("albumBindModel", albumBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.albumBindModel", bindingResult);
            return "redirect:/albums/add";
        }
        AlbumServiceModel albumServiceModel = modelMapper.map(albumBindModel, AlbumServiceModel.class)
                .setUserName(principal.getUsername());
        albumService.createAlbum(albumServiceModel);

        return "redirect:/home";
    }
}
