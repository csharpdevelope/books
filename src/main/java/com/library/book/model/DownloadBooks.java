package com.library.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DownloadBooks extends BaseEntity {
    private int count;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
}
