package com.library.book.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadRequest {
    @JsonProperty("book_id")
    private Long bookId;
}
