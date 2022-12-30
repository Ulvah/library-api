package com.awaliyah.libraryapi.repository;

import com.awaliyah.libraryapi.entity.BookType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTypeRepository extends PagingAndSortingRepository<BookType, Long> {

    Optional<BookType> findBookTypeByGuid(String guid);
}
