package com.awaliyah.libraryapi.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(indexes = {@Index(name = "IDX_BK_TYPE", columnList = "guid")})
public class BookType extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeNameId;
    private String typeName;

    @OneToMany(mappedBy = "bookType", cascade = CascadeType.ALL)
    private List<Book> books;

}
