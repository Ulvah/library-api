package com.awaliyah.libraryapi.service;

import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.entity.BookType;
import com.awaliyah.libraryapi.exception.BookTypeServiceException;
import com.awaliyah.libraryapi.exception.ResourceNotFoundException;
import com.awaliyah.libraryapi.repository.BookRepository;
import com.awaliyah.libraryapi.repository.BookTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookTypeService {

    private BookTypeRepository bookTypeRepository;
    private BookRepository bookRepository;

    @Autowired
    public BookTypeService(BookTypeRepository bookTypeRepository, BookRepository bookRepository) {
        this.bookTypeRepository = bookTypeRepository;
        this.bookRepository = bookRepository;
    }

    public BookType addNewBookType(String bookTypeName){
        if(!StringUtils.hasText(bookTypeName)) {
            throw new IllegalArgumentException("Book Type name can not be empty or null");
        }

        BookType bookType = new BookType();
        bookType.setGuid(UUID.randomUUID().toString());
        bookType.setTypeName(bookTypeName);
        return this.bookTypeRepository.save(bookType);
    }

    public BookType updateBookType(String guid, String bookTypeName) {
        if(guid == null || !StringUtils.hasText(bookTypeName)) {
            throw new IllegalArgumentException("Invalid parameters to update Book Type");
        }

        BookType retrievedBookType = this.bookTypeRepository.findBookTypeByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book Type not found")
        );

        retrievedBookType.setTypeName(bookTypeName);
        return this.bookTypeRepository.save(retrievedBookType);
    }

    public void deleteBookType(String guid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException(("Book type guid can not be empty or null"));
        }

        BookType retrievedBookType = this.bookTypeRepository.findBookTypeByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book type not found")
        );

        List<Book> books = this.bookRepository.findByBookTypeGuid(guid);
        if(!CollectionUtils.isEmpty(books)){
            throw new BookTypeServiceException("It is not possible to delete given book type as it has been used by book list");
        }

        this.bookTypeRepository.delete(retrievedBookType);

    }

    public Iterable<BookType> findAllBookTypes() {
        return this.bookTypeRepository.findAll();
    }

    public BookType findBookTypeByGuid(String guid) {
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException(("Book type guid can not be empty or null"));
        }
        return this.bookTypeRepository.findBookTypeByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Book type not found")
        );
    }
}
