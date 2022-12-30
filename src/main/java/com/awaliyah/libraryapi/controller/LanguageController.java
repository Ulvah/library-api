package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.dto.LanguageDTO;
import com.awaliyah.libraryapi.service.LanguageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/v1/languages")
public class LanguageController {
    private LanguageService languageService;
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
        return null;
    }
}
