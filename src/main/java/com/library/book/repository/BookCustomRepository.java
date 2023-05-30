package com.library.book.repository;

import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.SearchFilter;

import java.util.List;

public interface BookCustomRepository {
    List<BookDto> findSearchBook(SearchFilter searchFilter);
}
