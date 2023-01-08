package com.example.library.controller;

import com.example.library.models.Author;
import com.example.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/new")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author){
        return ResponseEntity.ok()
                .body(authorService.saveAuthor(author));
    }
}
