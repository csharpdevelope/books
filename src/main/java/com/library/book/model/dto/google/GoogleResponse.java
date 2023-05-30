package com.library.book.model.dto.google;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleResponse {
    private List<GoogleBookResponse> items;
}
