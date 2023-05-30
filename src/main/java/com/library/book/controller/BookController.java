package com.library.book.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.library.book.model.UploadType;
import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.SearchFilter;
import com.library.book.model.dto.request.DownloadRequest;
import com.library.book.model.dto.response.BookListDto;
import com.library.book.model.dto.response.JSONResponse;
import com.library.book.service.BookService;
import com.library.book.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<BookDto> books = bookService.getBookList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("by/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        BookDto bookDto = bookService.getBookById(id);
        if (bookDto == null) {
            String errorMessage = "Ushbu id=" + id + " bo'yicha kitob topilmadi.";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping("save")
    public ResponseEntity<JsonNode> saveBook(@RequestBody BookDto bookDto) {
        JsonNode bookId = bookService.saveBook(bookDto, UploadType.UPLOAD);
        return ResponseEntity.ok(bookId);
    }

    @PostMapping("upload")
    public ResponseEntity<JsonNode> uploadBook(@RequestPart(name = "file") MultipartFile file) {
        JsonNode fileId = bookService.uploadFile(file);
        return ResponseEntity.ok(fileId);
    }

    @GetMapping("count")
    public ResponseEntity<JsonNode> countBook() {
        JsonNode countBooks = bookService.countBooks();
        return ResponseEntity.ok(countBooks);
    }

    @PostMapping("search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestBody SearchFilter filter) {
        List<BookDto> books = bookService.searchBooks(filter);
        return ResponseEntity.ok(books);
    }

    @GetMapping("home")
    public ResponseEntity<JSONResponse> homePage() {
        BookListDto list = bookService.getAllBooks();
        return ResponseEntity.ok(new JSONResponse(list));
    }

    @GetMapping("statistic")
    public ResponseEntity<JsonNode> getStatistic() {
        return ResponseEntity.ok(bookService.getStatistic());
    }

    @PostMapping("download")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> downloadBooks(@RequestBody DownloadRequest request, HttpServletResponse response) {
        return bookService.downloadBooks(securityUtils.currentUser(), request, response);
    }
}
