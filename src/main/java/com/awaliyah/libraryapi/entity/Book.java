package com.awaliyah.libraryapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes = {@Index(name = "IDX_BOOK", columnList = "guid")})
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(unique = true)
    private String isbn;

    private String title;
    private String writer;
    private String publisher;

    @ManyToOne()
    private BookType bookType;

    @ManyToOne()
    private Language language;


}
