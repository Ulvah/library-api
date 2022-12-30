package com.awaliyah.libraryapi.repository;

import com.awaliyah.libraryapi.entity.Language;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {

    Optional<Language> findLanguageByGuid(String guid);
}
