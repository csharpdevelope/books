package com.library.book.repository;

import com.library.book.model.Book;
import com.library.book.model.DownloadBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadBooksRepository extends JpaRepository<DownloadBooks, Long> {

    @Query(value = "select d from DownloadBooks d order by d.count desc limit 10")
    List<DownloadBooks> findAllByCountIdOrderByCount();

    DownloadBooks findByBook(Book book);
}
