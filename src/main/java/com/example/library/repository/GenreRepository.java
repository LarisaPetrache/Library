package com.example.library.repository;

import com.example.library.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByGenreId(Integer id);

    List<Genre> findGenreByNameContainingIgnoreCase(String name);
}
