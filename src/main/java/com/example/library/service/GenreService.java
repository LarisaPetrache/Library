package com.example.library.service;

import com.example.library.models.Genre;
import com.example.library.repository.GenreRepository;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre saveGenre(Genre genre){
        return genreRepository.save(genre);
    }
}
