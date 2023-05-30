package com.library.book.model;

import com.library.book.model.dto.BookDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends BaseEntity {
    private String uuid;
    private String title;
    private String author;
    private String publisherDate;
    private String isbn;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @Enumerated(EnumType.STRING)
    private UploadType type;
    private Integer pageCount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileInfo fileInfo;

    public Book() {
    }

    public Book(String title, String author, String publisherDate, String isbn, String description) {
        this.title = title;
        this.author = author;
        this.publisherDate = publisherDate;
        this.isbn = isbn;
        this.description = description;
    }

    public Book(String title, String author, String publisherDate, String isbn, String description, String image) {
        this.title = title;
        this.author = author;
        this.publisherDate = publisherDate;
        this.isbn = isbn;
        this.description = description;
        this.image = image;
    }

    public Book(String title, String author, String publisherDate, String isbn, String description, String image, User user) {
        this.title = title;
        this.author = author;
        this.publisherDate = publisherDate;
        this.isbn = isbn;
        this.description = description;
        this.image = image;
        this.user = user;
    }

    public BookDto toDto() {
        BookDto book = new BookDto(getTitle(), getAuthor(), getPublisherDate(), getIsbn(), getDescription(), getImage());
        book.setId(getId());
        book.setFileId(getFileInfo().getId());
        return book;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + '}';
    }

}
