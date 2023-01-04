package com.awaliyah.libraryapi.repository;

import com.awaliyah.libraryapi.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findBookByGuid(String guid);
    Optional<Book> findBookByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE UPPER(b.title) LIKE UPPER(CONCAT('%', ?1, '%'))")
    List<Book> findBookByTitle(String title);

    @Query("SELECT b FROM Book b WHERE UPPER(b.writer) LIKE UPPER(CONCAT('%', ?1, '%'))")
    List<Book> findBookByWriter(String writer);

    @Query("SELECT b FROM Book b WHERE UPPER(b.publisher) LIKE UPPER(CONCAT('%', ?1, '%'))")
    List<Book> findBookByPublisher(String publisher);
    List<Book> findByLanguageGuid(String guid);
    List<Book> findByBookTypeGuid(String guid);
}
