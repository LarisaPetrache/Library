package com.example.library.controller;

import com.example.library.dto.AuthorRequest;
import com.example.library.mapper.AuthorMapper;
import com.example.library.models.Author;
import com.example.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorMapper authorMapper;

    @Test
    public void addNewGenre_saveAuthor() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Eula");
        authorRequest.setLastName("Lawrence");
        authorRequest.setBirthYear(1990);
        authorRequest.setNationality("british");

        Author author = new Author(1, "Eula", "Lawrence", "british", 1990);

        when(authorMapper.authorRequestToAuthor(any(AuthorRequest.class))).thenReturn(author);
        when(authorService.saveAuthor(author)).thenReturn(author);

        ResponseEntity<Author> response = authorController.saveAuthor(authorRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/author/1", Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(author, response.getBody());
    }

    @Test
    public void getAllAuthors() throws Exception {
        List<Author> authors = Arrays.asList(
                new Author(1, "FirstName1", "LastName1", "Nationality1", 1991),
                new Author(2, "FirstName2", "LastName2", "Nationality2", 1992)
        );

        when(authorService.findAllAuthors()).thenReturn(authors);

        assertEquals(HttpStatus.OK, authorController.getAllAuthors().getStatusCode());
        verify(authorService, times(1)).findAllAuthors();
    }

    @Test
    public void idExist_getGenreById() {
        int id = 1;
        Author author = new Author(1, "FirstName1", "LastName1", "Nationality1", 1991);

        when(authorService.findAuthorById(id)).thenReturn(author);

        ResponseEntity<?> responseEntity = authorController.getAuthorById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(author, responseEntity.getBody());

        verify(authorService, times(1)).findAuthorById(id);
    }

    @Test
    public void idDoesntExist_getGenreById(){
        int id = 1;
        when(authorService.findAuthorById(id)).thenReturn(null);

        ResponseEntity<?> responseEntity = authorController.getAuthorById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(authorService, times(1)).findAuthorById(id);
    }
}
