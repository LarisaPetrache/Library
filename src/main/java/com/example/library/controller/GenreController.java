package com.example.library.controller;

import com.example.library.models.Genre;
import com.example.library.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/new")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
        return ResponseEntity.ok()
                .body(genreService.saveGenre(genre));
    }
}
