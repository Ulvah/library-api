package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.dto.BookTypeDTO;
import com.awaliyah.libraryapi.dto.ResourceGuidDTO;
import com.awaliyah.libraryapi.entity.BookType;
import com.awaliyah.libraryapi.exception.ValidationException;
import com.awaliyah.libraryapi.service.BookTypeService;
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
@RequestMapping("api/v1/book-types")
public class BookTypeContoller {

    private BookTypeService bookTypeService;
    public BookTypeContoller(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookTypeDTO>> getAllBookTypes() {

        List<BookTypeDTO> resp = StreamSupport.stream(this.bookTypeService.findAllBookTypes().spliterator(), false)
                .map(bookType -> BookTypeDTO.toDTO(bookType))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceGuidDTO> createNewBookType(@RequestBody BookTypeDTO bookTypeDTO) {

        BookType newBookType = this.bookTypeService.addNewBookType(bookTypeDTO.getTypeName());
        return new ResponseEntity<>(new ResourceGuidDTO(newBookType.getGuid()), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBook(@RequestBody BookTypeDTO bookTypeDTO) {

        if(!StringUtils.hasText(bookTypeDTO.getGuid())) {
            throw  new ValidationException("Book type guid is mandatory");
        }

        this.bookTypeService.updateBookType(bookTypeDTO.getGuid(), bookTypeDTO.getTypeName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBookType(@PathVariable String guid) {
        this.bookTypeService.deleteBookType(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
