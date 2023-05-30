package com.library.book.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.book.exception.BadRequestException;
import com.library.book.exception.NotFoundException;
import com.library.book.exception.StorageException;
import com.library.book.model.*;
import com.library.book.model.dto.BookDto;
import com.library.book.model.dto.SearchFilter;
import com.library.book.model.dto.request.DownloadRequest;
import com.library.book.model.dto.response.BookListDto;
import com.library.book.repository.BookRepository;
import com.library.book.repository.DownloadBooksRepository;
import com.library.book.repository.FileInfoRepository;
import com.library.book.service.BookService;
import com.library.book.service.FileInfoService;
import com.library.book.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;
    private final FileInfoService fileInfoService;
    private final FileInfoRepository fileInfoRepository;
    private final DownloadBooksRepository downloadBooksRepository;
    private final UserService userService;

    private static final String DEFAULT_IMAGE = "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg";

    @Override
    public List<BookDto> getBookList() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookList = books
                .stream()
                .map(Book::toDto)
                .collect(Collectors.toList());

        return bookList;
    }

    @Override
    public BookDto getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(Book::toDto).orElse(null);
    }

    @Override
    public JsonNode saveBook(BookDto bookDto, UploadType type) {
        if (bookDto.getFileId() == null) {
            throw new BadRequestException("File is required");
        }
        Book book = new Book(bookDto.getTitle(),bookDto.getAuthor(), bookDto.getPublisherDate(), bookDto.getIsbn(), bookDto.getDescription());
        book.setImage(bookDto.getImage() != null && !bookDto.getImage().isEmpty() ? bookDto.getImage() : DEFAULT_IMAGE);
        book.setType(type);
        book.setFileInfo(fileInfo(bookDto.getFileId(), bookDto.getTitle()));
        bookRepository.save(book);
        logger.info("Book save id={}", book.getId());
        ObjectNode response = objectMapper.createObjectNode();
        response.put("id", book.getId());
        response.put("title", book.getTitle());
        response.put("author", book.getAuthor());
        return response;
    }

    @Override
    public JsonNode countBooks() {
        Long countBook = bookRepository.countAllBook();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("count", countBook);
        return response;
    }

    @Override
    public List<BookDto> searchBooks(SearchFilter filter) {
        return bookRepository.findSearchBook(filter);
    }

    @Override
    public BookListDto getAllBooks() {
        BookListDto list = new BookListDto();
        List<User> users = userService.findAllUser();
        list.setActiveUsers(users.stream().map(User::toDtoList)
                        .collect(Collectors.toList()));
        list.setTrendingBooks(trendingBookDtos());
        return list;
    }

    @Override
    public ResponseEntity<byte[]> downloadBooks(User currentUser, DownloadRequest request, HttpServletResponse response) {
        if (request.getBookId() == null) {
            throw new NotFoundException("Book Not found");
        }
        Book book = getBook(request.getBookId());
        DownloadBooks downloadBooks = downloadBooksRepository.findByBook(book);
        if (downloadBooks == null) {
            downloadBooks = new DownloadBooks();
        }
        downloadBooks.setBook(book);
        downloadBooks.setCount(downloadBooks.getCount() + 1);
        downloadBooksRepository.save(downloadBooks);
        return downloadBookResponse(book.getFileInfo(), response);
    }

    private ResponseEntity<byte[]> downloadBookResponse(FileInfo fileInfo, HttpServletResponse response) {
        try {
            byte[] resource = fileInfoService.loadAsResource(fileInfo.getFilename());
            return ResponseEntity.ok()
                    .header("file", "filename=\"" + fileInfo.getOriginalFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public JsonNode uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("File empty");
        }
        String filename = getFullFileName(file.getOriginalFilename());
        ObjectNode response = objectMapper.createObjectNode();
        FileInfo fileInfo = fileInfoService.uploadFile(file, filename, file.getOriginalFilename());
        response.put("id", fileInfo.getId());
        response.put("filename", fileInfo.getFilename());
        return response;
    }

    @Override
    public JsonNode getStatistic() {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("count", bookRepository.countAllBook());
        response.put("download_count", userService.count());
        return response;
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book Not Found"));
    }

    private String getFullFileName(String filename) {
        if (filename == null) {
            throw new StorageException("This filename is null " + filename);
        }
        String fileNamePrefix = Objects.requireNonNull(StringUtils.split(filename, "."))[0];
        String fileExtention = StringUtils.getFilenameExtension(filename);
        UUID hashString = UUID.randomUUID();
        return fileNamePrefix.replaceAll("\\s", "_") + hashString + "." + fileExtention;
    }

    private FileInfo fileInfo(Long fileId, String title) {
        FileInfo info = fileInfoRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File Not found"));
        info.setOriginalFilename(title);
        fileInfoRepository.save(info);
        return info;
    }

    private List<BookDto> trendingBookDtos() {
        List<Book> books = trendingBooks();
        if (books.isEmpty()) {
            Page<Book> page = bookRepository.findAll(PageRequest.of(0, 10));
            books = page.getContent();
        }

        return books
                .stream()
                .map(Book::toDto)
                .collect(Collectors.toList());
    }

    private List<Book> trendingBooks() {
        List<DownloadBooks> downloadBooks = downloadBooksRepository.findAllByCountIdOrderByCount();
        return bookRepository.findAllByIdIn(downloadBooks
                .stream()
                .map(DownloadBooks::getBook)
                .map(Book::getId)
                .collect(Collectors.toList()));
    }
}
