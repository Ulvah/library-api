package com.awaliyah.libraryapi.dto;

import com.awaliyah.libraryapi.entity.BookType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookTypeDTO {

    private String guid;
    private String typeName;

    public static BookTypeDTO toDTO(BookType bookType) {
        return BookTypeDTO.builder()
                .guid(bookType.getGuid())
                .typeName(bookType.getTypeName())
                .build();
    }
}
