package com.example.library.service;

import com.example.library.exception.GenreAlreadyExistException;
import com.example.library.models.Genre;
import com.example.library.repository.GenreRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findGenreById(Integer id) {
        return genreRepository.findByGenreId(id);
    }

    @Transactional
    @Modifying
    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public List<Genre> searchGenre(String name) {
        return genreRepository.findGenreByNameContainingIgnoreCase(name);
    }

    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }
}
