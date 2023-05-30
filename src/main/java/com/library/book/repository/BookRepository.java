package com.library.book.repository;

import com.library.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {
    List<Book> findByAuthor(String author);

    @Query(value = "select count(*) from books", nativeQuery = true)
    long countAllBook();

    List<Book> findAllByIdIn(List<Long> ids);
}
