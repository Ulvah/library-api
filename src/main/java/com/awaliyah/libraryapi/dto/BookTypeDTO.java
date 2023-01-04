package com.awaliyah.libraryapi.dto;

import com.awaliyah.libraryapi.entity.BookType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class BookTypeDTO {

    private String guid;

    @NotBlank(message = "Book type can not be null or empty")
    private String typeName;

    public static BookTypeDTO toDTO(BookType bookType) {
        return BookTypeDTO.builder()
                .guid(bookType.getGuid())
                .typeName(bookType.getTypeName())
                .build();
    }
}
