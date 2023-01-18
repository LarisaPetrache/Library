package com.example.library.mapper;

import com.example.library.dto.GenreRequest;
import com.example.library.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public Genre genreRequestToGenre(GenreRequest genreRequest){
        return new Genre(genreRequest.getName());
    }
}
