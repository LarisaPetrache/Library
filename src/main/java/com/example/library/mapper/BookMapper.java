package com.example.library.mapper;

import com.example.library.dto.BookRequest;
import com.example.library.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book bookRequestToBook(BookRequest bookRequest){
        return new Book(bookRequest.getTitle(), bookRequest.getIsbn(),
                bookRequest.getPublicationYear());
    }
}
