package com.awaliyah.libraryapi.dto;

import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.entity.BookType;
import com.awaliyah.libraryapi.entity.Language;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Builder
@Getter
public class BookDTO {

    private String guid;
    private String isbn;
    private String title;
    private String writer;
    private String publisher;
    private Language language;
    private BookType bookType;

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .guid(book.getGuid())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .writer(book.getWriter())
                .publisher(book.getPublisher())
                .language(book.getLanguage())
                .bookType(book.getBookType())
                .build();
    }
}
