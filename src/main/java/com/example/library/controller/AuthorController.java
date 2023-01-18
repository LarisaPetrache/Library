package com.example.library.controller;

import com.example.library.dto.AuthorRequest;
import com.example.library.mapper.AuthorMapper;
import com.example.library.models.Author;
import com.example.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@Validated
@Tag(name = "✦ Author Controller", description = "/author")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Operation(summary = "Create an author",
            description = "Creates a new author based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The author was successfully created based on the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "authorRequest", description = "Author details", required = true)
    /* ====================
          Save author
    ======================= */
    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody @Valid AuthorRequest authorRequest){

        Author author = authorService.saveAuthor(authorMapper.authorRequestToAuthor(authorRequest));

        return ResponseEntity.created(URI.create("/author/" + author.getAuthorId()))
                .body(author);
    }

    @Operation(summary = "Get author based on specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Author information was successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "The Author with the provided ID doesn't exist")
    })
    /* ====================
        Get author by ID
    ======================= */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable int id) {
        Optional<Author> author = authorService.findAuthorById(id);

        if (author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(author);
    }

    @Operation(summary = "Get all authors")
    /* ====================
        Get all authors
    ======================= */
    @GetMapping("/list")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

}
