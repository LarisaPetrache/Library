package com.example.library.controller;

import com.example.library.dto.AuthorRequest;
import com.example.library.mapper.AuthorMapper;
import com.example.library.models.Author;
import com.example.library.service.AuthorService;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@Validated
@Api(value = "/author", tags = "Authors")
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    @ApiOperation(value = "Create an Author",
    notes = "Creates a new Author based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Author was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody
                                                 @ApiParam(name = "author", value = "Author details", required = true)
                                                 AuthorRequest authorRequest){

        Author author = authorService.saveAuthor(authorMapper.authorRequestToAuthor(authorRequest));

        return ResponseEntity.created(URI.create("/author/" + author.getAuthorId()))
                .body(author);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Author based on ID",
            notes = "Returns the Author with the ID provided")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Author information was successfully retrieved"),
            @ApiResponse(code = 404, message = "The Author with the provided ID doesn't exist")
    })
    public ResponseEntity<?> getAuthorById(@PathVariable int id) {
        Optional<Author> author = authorService.findAuthorById(id);

        if (author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(author);
    }

}
