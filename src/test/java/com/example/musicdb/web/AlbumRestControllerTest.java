package com.example.musicdb.web;

import com.example.musicdb.model.entity.AlbumEntity;
import com.example.musicdb.model.entity.ArtistEntity;
import com.example.musicdb.model.entity.UserEntity;
import com.example.musicdb.model.entity.enums.Genre;
import com.example.musicdb.repo.AlbumRepo;
import com.example.musicdb.repo.ArtistRepo;
import com.example.musicdb.repo.UserRepo;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // can send GET, POSt request
@AutoConfigureTestDatabase
class AlbumRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AlbumRepo albumRepo;
    @Autowired
    private ArtistRepo artistRepo;

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

        albumRepo.save(albumEntity);
    }

    @Test
    @WithMockUser(username = "John", roles = {"USER", "ADMIN"})
    public void findAll_ReturnOkBodyListOfAlbumEntity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/albums/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("JUSTICE FOR ALL"));
    }

}