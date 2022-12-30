package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.dto.BookDTO;
import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.exception.ValidationException;
import com.awaliyah.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("api/v1/books")
public class BookController {

    private BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooks() {

        List<BookDTO> resp = StreamSupport.stream(this.bookService.findAllBooks().spliterator(), false)
                .map(book -> BookDTO.toDTO(book))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewBook(@RequestBody BookDTO bookDTO) {
        if(bookDTO.getLanguage() == null) {
            throw new ValidationException("Language can not null");
        }

        if(bookDTO.getBookType() == null) {
            throw new ValidationException("Book type can not null");
        }

        Book newBook = this.bookService.addBook(bookDTO.getIsbn(), bookDTO.getTitle(),
                bookDTO.getWriter(), bookDTO.getPublisher(), bookDTO.getLanguage().getGuid(),
                bookDTO.getBookType().getGuid());

        return new ResponseEntity<>(newBook.getGuid(), HttpStatus.CREATED);
    }


}
