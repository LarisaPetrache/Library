package com.example.library.service;

import com.example.library.models.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> findAuthorById(int id) {
        return authorRepository.findById(id);
    }
}
