package com.example.musicdb.web;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.entity.ArtistEntity;
import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.entity.enums.Genre;
import com.example.musicdb.repo.AlbumRepo;
import com.example.musicdb.repo.ArtistRepo;
import com.example.musicdb.repo.LogRepo;
import com.example.musicdb.repo.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc // can send GET, POSt request
@AutoConfigureTestDatabase
class AlbumControllerTest {
    private static final String ALBUM_CONTROLLER_PREFIX = "/albums";

    private long testAlbumId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AlbumRepo albumRepo;
    @Autowired
    private ArtistRepo artistRepo;
    @Autowired
    private LogRepo logRepo;

    @BeforeEach
    public void setup() {
        ArtistEntity artistEntity = new ArtistEntity()
                .setName("METALLICA").setCareerInformation("Info...");
        artistRepo.save(artistEntity);

        UserEntity userEntity = new UserEntity().setUsername("John").setPassword("123")
                .setFullName("John Smith").setEmail("a@b.c");
        userRepo.save(userEntity);

        AlbumEntity albumEntity = new AlbumEntity()
                .setName("JUSTICE FOR ALL")
                .setImageUrl("http://a").setVideoUrl("http://b")
                .setDescription("Some description...").setCopies(1111111)
                .setPrice(BigDecimal.TEN)
                .setReleaseDate(LocalDate.of(1988, 3, 3)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())
                .setGenre(Genre.METAL)
                .setArtistEntity(artistEntity)
                .setUserEntity(userEntity);

        albumEntity = albumRepo.save(albumEntity);
        testAlbumId = albumEntity.getId();
    }

    @AfterEach
    public void cleanData() {
        this.logRepo.deleteAll();
        this.albumRepo.deleteAll();
        this.artistRepo.deleteAll();
        this.userRepo.deleteAll();
    }

    @Test
    @WithMockUser(value = "John", roles = {"USER", "ADMIN"})
    public void getAddAlbumPage_ReturnOk() throws Exception {
        mockMvc.perform(get(ALBUM_CONTROLLER_PREFIX + "/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-album"))
                .andExpect(model().attributeExists("artists"));
    }


//    @Test
//    @WithMockUser(value = "John", roles = {"USER", "ADMIN"})
//    public void addAlbum_ReturnRedirectAndRepoCount2() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post(ALBUM_CONTROLLER_PREFIX + "/add")
//                        .param("name", "test album")
//                        .param("genre", Genre.METAL.name())
//                        .param("imageUrl", "http://c")
//                        .param("videoUrl", "http://d")
//                        .param("description", "description")
//                        .param("copies", "1111111")
//                        .param("price", "10")
//                        .param("releaseDate", "2000-01-01")
//                        .param("artist", "METALLICA")
//                        .with(csrf())) // mockMVC will send csrf token to the server
//                .andExpect(status().is3xxRedirection());
//
//        Assertions.assertEquals(2, albumRepo.count());
//    }

    @Test
    @WithMockUser(value = "John", roles = {"USER", "ADMIN"})
    public void addAlbum_ReturnRedirectAndAlbumBindModel() throws Exception {
        mockMvc.perform(post(ALBUM_CONTROLLER_PREFIX + "/add")
                        .param("name", "1")
                        .with(csrf())) // mockMVC will send csrf token to the server
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/albums/add"));
    }

//    @Test
//    @WithMockUser(value = "John", roles = {"USER", "ADMIN"}) // Spring Sec Context mock
//    public void getDetailsPage_ReturnValidStatusViewNameAndModel() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get(
//                        ALBUM_CONTROLLER_PREFIX + "/details/{id}", testAlbumId))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("albumViewModel"));
//    }
}