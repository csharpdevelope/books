package com.library.book.model.dto.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VolumeInfo {
    private String title;
    @JsonProperty("subtitle")
    private String subtitle;
    @JsonProperty("authors")
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private List<IndustryIdentifiers> industryIdentifiers;
    private Long pageCount;
    private GoogleImage imageLinks;
}
