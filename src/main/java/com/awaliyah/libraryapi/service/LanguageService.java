package com.awaliyah.libraryapi.service;

import com.awaliyah.libraryapi.entity.Book;
import com.awaliyah.libraryapi.entity.Language;
import com.awaliyah.libraryapi.exception.LanguageServiceException;
import com.awaliyah.libraryapi.exception.ResourceNotFoundException;
import com.awaliyah.libraryapi.repository.BookRepository;
import com.awaliyah.libraryapi.repository.LanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LanguageService {

    private LanguageRepository languageRepository;
    private BookRepository bookRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, BookRepository bookRepository){
        this.languageRepository = languageRepository;
        this.bookRepository = bookRepository;
    }

    public Language addNewLanguage(String languageName){

        if(!StringUtils.hasText(languageName)){
            throw new IllegalArgumentException(("Language name can not be empty or null"));
        }

        Language language = new Language();
        language.setGuid(UUID.randomUUID().toString());
        language.setLanguageName(languageName);
        return this.languageRepository.save(language);
    }

    public Language updateLanguage(String guid, String languageName){

        if(guid == null || !StringUtils.hasText(languageName)) {
            throw new IllegalArgumentException("Invalid parameters to update language");
        }

        Language retrievedLanguage = this.languageRepository.findLanguageByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("The Language not found")
        );

        retrievedLanguage.setLanguageName(languageName);
        return this.languageRepository.save(retrievedLanguage);

    }

    public void deleteLanguage(String guid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException(("Language guid can not be empty or null"));
        }

        Language retrievedLanguage = this.languageRepository.findLanguageByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("The Language not found")
        );

        List<Book> books = this.bookRepository.findByLanguageGuid(guid);
        if(!CollectionUtils.isEmpty(books)){
            throw new LanguageServiceException("It is not possible to delete given language as it has been used by book list");
        }

        this.languageRepository.delete(retrievedLanguage);

    }

    public Iterable<Language> findAllLanguages() {
        return this.languageRepository.findAll();
    }

    public Language findLanguageByGuid(String guid) {
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException(("Language guid can not be empty or null"));
        }
        return this.languageRepository.findLanguageByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("This language not found")
        );
    }
}
