package com.example.musicdb.model.binding;

import com.example.musicdb.model.entity.enums.Genre;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AlbumBindModel {
    @Size(min = 3, max = 20)
    private String name;
    private String imageUrl;
    private String videoUrl;
    @Size(min = 10)
    private String description;
    @Min(0)
    private Integer copies;
    @DecimalMin("0")
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @NotNull
    private Genre genre;
    @NotNull
    private String artist;

    public String getArtist() {
        return artist;
    }

    public AlbumBindModel setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getName() {
        return name;
    }

    public AlbumBindModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AlbumBindModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public AlbumBindModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AlbumBindModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCopies() {
        return copies;
    }

    public AlbumBindModel setCopies(Integer copies) {
        this.copies = copies;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AlbumBindModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public AlbumBindModel setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public AlbumBindModel setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }
}
