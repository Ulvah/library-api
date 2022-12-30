package com.awaliyah.libraryapi.service;

import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.entity.BookType;
import com.awaliyah.libraryapi.entity.Language;
import com.awaliyah.libraryapi.exception.ResourceNotFoundException;
import com.awaliyah.libraryapi.repository.BookRepository;
import com.awaliyah.libraryapi.repository.BookTypeRepository;
import com.awaliyah.libraryapi.repository.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookService {

    private BookRepository bookRepository;

    private LanguageRepository languageRepository;

    private BookTypeRepository bookTypeRepository;

    @Autowired
    public BookService(BookRepository bookRepository, LanguageRepository languageRepository, BookTypeRepository bookTypeRepository) {
        this.bookRepository = bookRepository;
        this.languageRepository = languageRepository;
        this.bookTypeRepository = bookTypeRepository;
    }

    public void validateBook(String isbn, String title, String writer, String publisher, String bookTypeGuid, String languageGuid) {

        if(!StringUtils.hasText(isbn)){
            throw new IllegalArgumentException("isbn is empty");
        }

        if(!StringUtils.hasText(title)){
            throw new IllegalArgumentException("title is empty");
        }

        if(!StringUtils.hasText(writer)){
            throw new IllegalArgumentException("writer is empty");
        }

        if(!StringUtils.hasText(publisher)){
            throw new IllegalArgumentException("publisher is empty");
        }

        if(!StringUtils.hasText(languageGuid)){
            throw new IllegalArgumentException("Language is empty");
        }

        if(!StringUtils.hasText(bookTypeGuid)){
            throw new IllegalArgumentException("Book type is empty");
        }

    }

    public Book addBook(String isbn, String title, String writer, String publisher, String languageGuid, String bookTypeGuid) {

        this.validateBook(isbn, title, writer, publisher, languageGuid, bookTypeGuid);

        Language retrievedLanguage = languageRepository.findLanguageByGuid(languageGuid).orElseThrow(
                () -> new ResourceNotFoundException("Language not found")
        );

        BookType retrievedBookType = bookTypeRepository.findBookTypeByGuid(bookTypeGuid).orElseThrow(
                () -> new ResourceNotFoundException("Book type not found")
        );

        Book book = new Book();
        book.setGuid(UUID.randomUUID().toString());
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setWriter(writer);
        book.setPublisher(publisher);
        book.setLanguage(retrievedLanguage);
        book.setBookType(retrievedBookType);

        return bookRepository.save(book);
    }

    public Iterable<Book> findAllBooks() {
        return this.bookRepository.findAll();
    }

    public void deleteBook(String guid) {

        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid is empty");
        }
        Book retrievedBook = this.bookRepository.findBookByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book not found")
        );
        this.bookRepository.delete(retrievedBook);
    }

    public Book updateBook(String guid, String isbn, String title, String writer, String publisher, String bookTypeGuid, String languageGuid) {
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid is empty");
        }
        Book retrievedBook = this.bookRepository.findBookByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book not exist")
        );

        if (StringUtils.hasText(isbn)) {
            retrievedBook.setIsbn(isbn);
        }

        if (StringUtils.hasText(title)) {
            retrievedBook.setTitle(title);
        }

        if (StringUtils.hasText(writer)) {
            retrievedBook.setWriter(writer);
        }

        if (StringUtils.hasText(publisher)) {
            retrievedBook.setPublisher(publisher);
        }

        if (StringUtils.hasText(bookTypeGuid)) {
            BookType retrievedBookType = bookTypeRepository.findBookTypeByGuid(bookTypeGuid).orElseThrow(
                    () -> new ResourceNotFoundException("Book type not found")
            );

            retrievedBook.setBookType(retrievedBookType);
        }

        if (StringUtils.hasText(languageGuid)) {
            Language retrievedLanguage = languageRepository.findLanguageByGuid(languageGuid).orElseThrow(
                    () -> new ResourceNotFoundException("Language not found")
            );

            retrievedBook.setLanguage(retrievedLanguage);
        }

        return retrievedBook;
    }

    public Book findBookByGuid(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Book guid can not be empty or null");
        }

        Book retrievedBook = bookRepository.findBookByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book not found")
        );

        return retrievedBook;
    }

    public Book findBookByIsbn (String isbn) {
        if(!StringUtils.hasText(isbn)) {
            throw new IllegalArgumentException("Book isbn can not be empty or null");
        }

        Book retrievedBook = bookRepository.findBookByIsbn(isbn).orElseThrow(
                () -> new ResourceNotFoundException("Book not found")
        );

        return retrievedBook;
    }

    public List<Book> findBookByTitle(String title) {
        if(!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("Title can not be empty");
        }

        return bookRepository.findBookByTitle(title);
    }

    public List<Book> findBookByWriter(String writer) {
        if(!StringUtils.hasText(writer)) {
            throw new IllegalArgumentException("Witer can not be null");
        }

        return bookRepository.findBookByWriter(writer);

    }

    public List<Book> findBookByPublisher(String publisher) {
        if(!StringUtils.hasText(publisher)) {
            throw new IllegalArgumentException("Publisher can not be null");
        }
        return bookRepository.findBookByPublisher(publisher);
    }

}
