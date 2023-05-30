package com.library.book.repository.impl;

import com.library.book.model.Book;
import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.SearchFilter;
import com.library.book.repository.BookCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

public class BookCustomRepositoryImpl implements BookCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<BookDto> findSearchBook(SearchFilter searchFilter) {
        StringBuilder builder = new StringBuilder();
        builder.append("select b from Book b where ");

        if (searchFilter.getTitle() != null) {
            builder.append("b.title like '%").append(searchFilter.getTitle()).append("%'");
        }
        List<Book> books = entityManager.createQuery(builder.toString(), Book.class).getResultList();
        return books.stream().map(Book::toDto)
                .collect(Collectors.toList());
    }
}
