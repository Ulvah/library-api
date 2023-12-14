package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.dto.LanguageDTO;
import com.awaliyah.libraryapi.dto.ResourceGuidDTO;
import com.awaliyah.libraryapi.entity.Language;
import com.awaliyah.libraryapi.exception.ValidationException;
import com.awaliyah.libraryapi.service.LanguageService;
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
@RequestMapping("api/v1/languages")
public class LanguageController {
    private LanguageService languageService;
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {

        List<LanguageDTO> resp = StreamSupport.stream(this.languageService.findAllLanguages().spliterator(), false)
                .map(language -> LanguageDTO.toDTO(language))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceGuidDTO> createNewBookType(@RequestBody LanguageDTO languageDTO) {

        Language newLanguage = this.languageService.addNewLanguage(languageDTO.getLanguageName());
        return new ResponseEntity<>(new ResourceGuidDTO(newLanguage.getGuid()), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateLanguage(@RequestBody LanguageDTO languageDTO) {

        if(!StringUtils.hasText(languageDTO.getGuid())) {
            throw  new ValidationException("Language guid is mandatory");
        }

        this.languageService.updateLanguage(languageDTO.getGuid(), languageDTO.getLanguageName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{guid}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable String guid) {
        this.languageService.deleteLanguage(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
