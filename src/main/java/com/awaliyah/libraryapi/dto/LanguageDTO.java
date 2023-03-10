package com.awaliyah.libraryapi.dto;

import com.awaliyah.libraryapi.entity.BookType;
import com.awaliyah.libraryapi.entity.Language;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LanguageDTO {

    private String guid;

    @NotBlank(message = "Language can not be null or empty")
    private String languageName;
    public static LanguageDTO toDTO(Language language) {
        return LanguageDTO.builder()
                .guid(language.getGuid())
                .languageName(language.getLanguageName())
                .build();
    }
}
