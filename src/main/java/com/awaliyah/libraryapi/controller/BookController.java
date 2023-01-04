package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.dto.BookDTO;
import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.exception.ValidationException;
import com.awaliyah.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping(value = "{isbn}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBookByIsbn(@PathVariable String isbn) {

        List<BookDTO> resp = StreamSupport.stream(this.bookService.findBookByTitle(isbn).spliterator(), false)
                .map(book -> BookDTO.toDTO(book))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }
    @GetMapping(value = "{title}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBookByTitle(@PathVariable String title) {

        List<BookDTO> resp = StreamSupport.stream(this.bookService.findBookByTitle(title).spliterator(), false)
                .map(book -> BookDTO.toDTO(book))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @GetMapping(value = "{writer}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBookByWriter(@PathVariable String writer) {

        List<BookDTO> resp = StreamSupport.stream(this.bookService.findBookByTitle(writer).spliterator(), false)
                .map(book -> BookDTO.toDTO(book))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @GetMapping(value = "{publisher}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> findBookByPublisher(@PathVariable String publisher) {

        List<BookDTO> resp = StreamSupport.stream(this.bookService.findBookByTitle(publisher).spliterator(), false)
                .map(book -> BookDTO.toDTO(book))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBook(@RequestBody BookDTO bookDTO) {

        if(!StringUtils.hasText(bookDTO.getGuid())) {
            throw  new ValidationException("Book guid is mandatory");
        }

        this.bookService.updateBook(bookDTO.getGuid(), bookDTO.getIsbn(), bookDTO.getTitle(),
                bookDTO.getWriter(), bookDTO.getPublisher(), bookDTO.getBookType().getGuid(),
                bookDTO.getLanguage().getGuid());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBook(@PathVariable String guid) {
        this.bookService.deleteBook(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
