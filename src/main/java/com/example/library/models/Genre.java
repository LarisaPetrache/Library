package com.example.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreId;

    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();

    public Genre(){}

    public Genre(String name) {
        this.name = name;
    }

    public int getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
