package com.example.library.service;

import com.example.library.models.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Author findAuthorById(int id) {
        return authorRepository.findByAuthorId(id);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
