package com.library.book.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.library.book.model.UploadType;
import com.library.book.model.User;
import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.SearchFilter;
import com.library.book.model.dto.request.DownloadRequest;
import com.library.book.model.dto.response.BookListDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    List<BookDto> getBookList();

    BookDto getBookById(Long id);

    JsonNode saveBook(BookDto bookDto, UploadType type);

    JsonNode countBooks();

    List<BookDto> searchBooks(SearchFilter filter);

    BookListDto getAllBooks();

    ResponseEntity<byte[]> downloadBooks(User currentUser, DownloadRequest request, HttpServletResponse response);

    JsonNode uploadFile(MultipartFile file);

    JsonNode getStatistic();
}
