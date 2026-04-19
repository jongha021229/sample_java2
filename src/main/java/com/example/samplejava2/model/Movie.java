package com.example.samplejava2.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Movie {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private Double rating;

    private String synopsis;

    public Movie() {
    }

    public Movie(String title, Double rating, String synopsis) {
        this.title = title;
        this.rating = rating;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
