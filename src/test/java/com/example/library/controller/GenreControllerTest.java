package com.example.library.controller;

import com.example.library.dto.GenreRequest;
import com.example.library.exception.GenreAlreadyExistException;
import com.example.library.mapper.GenreMapper;
import com.example.library.models.Genre;
import com.example.library.repository.GenreRepository;
import com.example.library.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {

    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;

    @Mock
    private GenreMapper genreMapper;

    @Test
    public void addNewGenre_saveGenre() throws GenreAlreadyExistException {
        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setName("drama");

        Genre genre = new Genre(1, "drama");

        when(genreService.findByName(genreRequest.getName())).thenReturn(null);

        when(genreMapper.genreRequestToGenre(any(GenreRequest.class))).thenReturn(genre);
        when(genreService.saveGenre(genre)).thenReturn(genre);

        ResponseEntity<Genre> response = genreController.saveGenre(genreRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/genre/1", Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(genre, response.getBody());
    }

    @Test
    public void genreAlreadyExist_create_throwsGenreAlreadyExistException() {
        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setName("comedy");

        when(genreService.findByName(genreRequest.getName())).thenReturn(new Genre());

        try {
            genreController.saveGenre(genreRequest);
            fail();
        } catch (GenreAlreadyExistException e) {
            assertEquals("This genre already exist!", e.getMessage());
        }
    }

    @Test
    public void getAllGenres() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre(1, "Genre1"),
                new Genre(2, "Genre2"),
                new Genre(3, "Genre3")
        );

        when(genreService.findAllGenres()).thenReturn(genres);

        assertEquals(HttpStatus.OK, genreController.getAllGenres().getStatusCode());
        verify(genreService, times(1)).findAllGenres();
    }

    @Test
    public void idExist_getGenreById() {
        int id = 1;
        Genre genre = new Genre(1, "genre");

        when(genreService.findGenreById(id)).thenReturn(genre);

        ResponseEntity<?> responseEntity = genreController.getGenreById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(genre, responseEntity.getBody());

        verify(genreService, times(1)).findGenreById(id);
    }

    @Test
    public void idDoesntExist_getGenreById(){
        int id = 1;
        when(genreService.findGenreById(id)).thenReturn(null);

        ResponseEntity<?> responseEntity = genreController.getGenreById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(genreService, times(1)).findGenreById(id);
    }

    @Test
    public void searchedGenreExist_searchGenre(){
        List<Genre> genres = Arrays.asList(
                new Genre(1, "fiction"),
                new Genre(2, "science fiction")
        );

        String name = "fiction";

        when(genreService.searchGenre(name)).thenReturn(genres);

        assertEquals(HttpStatus.OK, genreController.searchGenre(name).getStatusCode());
        verify(genreService, times(1)).searchGenre(name);
    }

    @Test
    public void searchedGenreDoesntExist_searchGenre(){
        List<Genre> genres = null;

        String name = "fiction";

        when(genreService.searchGenre(name)).thenReturn(genres);

        assertEquals(HttpStatus.NOT_FOUND, genreController.searchGenre(name).getStatusCode());
        verify(genreService, times(1)).searchGenre(name);
    }

//    @Test
//    public void genreIdExist_updateGenreTest(){
//        String oldName = "previous genre name";
//        String newName = "new genre name";
//        int id = 1;
//
//        // Get genre from database based on ID
//        Genre genre = new Genre(id, oldName);
//        when(genreService.findGenreById(id)).thenReturn(genre);
//
//        // Updated genre
//        Genre savedGenre = new Genre();
//        savedGenre.setName(newName);
//        savedGenre.setGenreId(id);
//
//        when(genreService.updateGenre(genre)).thenReturn(savedGenre);
//
//        assertNotNull(savedGenre);
//        assertEquals(id, savedGenre.getGenreId());
//        assertEquals(newName, savedGenre.getName());
//    }
}
