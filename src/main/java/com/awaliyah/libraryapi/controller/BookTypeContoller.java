package com.awaliyah.libraryapi.controller;

import com.awaliyah.libraryapi.service.BookTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/book-types")
public class BookTypeContoller {

    private BookTypeService bookTypeService;
    public BookTypeContoller(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }
}
