package com.library.book.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    private String publisherDate;
    @NonNull
    private String isbn;
    private String description;
    private String image;
    @JsonProperty("file_id")
    private Long fileId;

    public BookDto() {
    }

    public BookDto(String title, String author, String publisherDate, String isbn, String description, String image) {
        this.title = title;
        this.author = author;
        this.publisherDate = publisherDate;
        this.isbn = isbn;
        this.description = description;
        this.image = image;
    }
}
