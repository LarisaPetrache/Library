package com.example.library.mapper;

import com.example.library.dto.AuthorRequest;
import com.example.library.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author authorRequestToAuthor(AuthorRequest authorRequest){
        return new Author(authorRequest.getFirstName(), authorRequest.getLastName(), authorRequest.getNationality(), authorRequest.getBirthYear());
    }
}
