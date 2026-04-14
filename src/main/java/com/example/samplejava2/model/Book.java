package com.example.samplejava2.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Book {

    @NotBlank(message = "제목은 필수입니다")
    @Size(min = 1, max = 200, message = "제목은 1~200자여야 합니다")
    private String title;

    @NotBlank(message = "저자는 필수입니다")
    @Size(min = 1, max = 100, message = "저자는 1~100자여야 합니다")
    private String author;

    @NotNull(message = "출판연도는 필수입니다")
    @Min(value = 1000, message = "출판연도는 1000 이상이어야 합니다")
    @Max(value = 2100, message = "출판연도는 2100 이하여야 합니다")
    private Integer year;

    @Size(max = 500, message = "설명은 500자 이하여야 합니다")
    private String description;

    public Book() {
    }

    public Book(String title, String author, Integer year, String description) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
