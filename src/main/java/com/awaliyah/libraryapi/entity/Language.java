package com.awaliyah.libraryapi.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(indexes = {@Index(name = "IDX_LANG", columnList = "guid")})
public class Language extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long languageId;

    @Column(unique = true)
    private String languageName;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
    private List<Book> books;

}
