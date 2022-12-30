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

    @Query(value = "FROM Book WHERE UPPER(title) LIKE %?#{[0].toUpperCase()}%")
    List<Book> findBookByTitle(String title);

    @Query(value = "FROM Book WHERE UPPER(writer) LIKE %?#{[0].toUpperCase()}%")
    List<Book> findBookByWriter(String writer);

    @Query(value = "FROM Book WHERE UPPER(publisher) LIKE %?#{[0].toUpperCase()}%")
    List<Book> findBookByPublisher(String publisher);
    List<Book> findByLanguageGuid(String guid);
    List<Book> findByBookTypeGuid(String guid);
}
