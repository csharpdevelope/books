package com.library.book.model.dto.response;

import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.UserListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookListDto {
    private List<BookDto> trendingBooks;
    private List<UserListDto> activeUsers;
}
