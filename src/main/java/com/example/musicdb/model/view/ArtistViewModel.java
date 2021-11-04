package com.example.musicdb.model.view;

public class ArtistViewModel {
    private Long id;
    private String name;
    private String careerInformation;

    public Long getId() {
        return id;
    }

    public ArtistViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArtistViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getCareerInformation() {
        return careerInformation;
    }

    public ArtistViewModel setCareerInformation(String careerInformation) {
        this.careerInformation = careerInformation;
        return this;
    }
}
