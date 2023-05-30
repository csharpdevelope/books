package com.library.book.model.dto.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleBookResponse {
    private String id;
    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;
}
