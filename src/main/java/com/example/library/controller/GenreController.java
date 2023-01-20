package com.example.library.controller;

import com.example.library.dto.GenreRequest;
import com.example.library.exception.GenreAlreadyExistException;
import com.example.library.mapper.GenreMapper;
import com.example.library.models.Genre;
import com.example.library.service.GenreService;
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

@RestController
@RequestMapping("/genre")
@Validated
@Tag(name = "✦ Genre Controller", description = "/genre")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @Operation(summary = "Add a new genre",
            description = "Adds a new genre based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The genre was successfully added based on the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "genreRequest", description = "Contains genre name", required = true)
    /* ====================
          Save genre
    ======================= */
    @PostMapping
    public ResponseEntity<Genre> saveGenre(@RequestBody @Valid GenreRequest genreRequest)
            throws GenreAlreadyExistException{

        if(genreService.findByName(genreRequest.getName()) != null)
            throw new GenreAlreadyExistException();

        Genre genre = genreService.saveGenre(genreMapper.genreRequestToGenre(genreRequest));

        return ResponseEntity.created(URI.create("/genre/" + genre.getGenreId()))
                .body(genre);
    }

    @Operation(summary = "Get all genres")
    /* ====================
          Get all genres
    ======================= */
    @GetMapping("/list")
    public ResponseEntity<List<Genre>> getAllGenres(){
        return ResponseEntity.ok().body(genreService.findAllGenres());
    }

    @Operation(summary = "Get genre by ID")
    /* ====================
        Get genre by ID
    ======================= */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable int id) {
        Genre genre = genreService.findGenreById(id);

        if (genre == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(genre);
    }

    @Operation(summary = "Update genre's name")
    /* ====================
        Update genre's name
    ======================= */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@RequestBody String newName, @PathVariable Integer id){

        if(genreService.findGenreById(id) == null)
            return ResponseEntity.badRequest().body("Provided ID doesn't exist");

        Genre genre = genreService.findGenreById(id);

        genre.setName(newName);

        Genre updatedGenre = genreService.updateGenre(genre);

        return ResponseEntity.ok().body("Genre name updated with the new name: " + updatedGenre.getName());
    }

    @Operation(summary = "Search genre",
            description = "Returns all genres containing the name provided")
    /* ====================
            Search genre
     ====================== */
    @GetMapping("/search")
    public ResponseEntity<?> searchGenre(@RequestBody String name){
        List<Genre> genresList = genreService.searchGenre(name);

        if (genresList == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(genresList);
    }
}
