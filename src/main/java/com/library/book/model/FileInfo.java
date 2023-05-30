package com.library.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "file_info")
@Getter
@Setter
@JsonIgnoreProperties({"createDate", "updateDate", "active"})
public class FileInfo extends BaseEntity {
    private String filename;
    private Long fileSize;
    private String originalFilename;
}