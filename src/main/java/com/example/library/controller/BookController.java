package com.example.library.controller;

import com.example.library.models.Book;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/new")
    public ResponseEntity<Book> saveBook(@RequestBody Book book,
                                         @RequestParam int authorId,
                                         @RequestParam int publisherId,
                                         @RequestParam int genreId){
        return ResponseEntity.ok()
                .body(bookService.saveBook(book, authorId, publisherId, genreId));
    }

}
