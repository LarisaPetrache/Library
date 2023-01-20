package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.exception.BookAlreadyExistException;
import com.example.library.mapper.BookMapper;
import com.example.library.models.Book;
import com.example.library.service.BookService;
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
@RequestMapping("/book")
@Validated
@Tag(name = "✦ Book Controller", description = "/book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Operation(summary = "Create a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The book was successfully created based on " +
                    "the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "bookRequest", description = "Book details", required = true)
    @Parameter(name = "authorId", description = "Author ID", required = true)
    @Parameter(name = "publisherId", description = "Publisher ID", required = true)
    @Parameter(name = "genreId", description = "Genre ID", required = true)
    /* ====================
            Save Book
     ====================== */
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody @Valid BookRequest bookRequest,
                                         @RequestParam int authorId,
                                         @RequestParam int publisherId,
                                         @RequestParam int genreId)
            throws BookAlreadyExistException {

        if(bookService.findByIsbn(bookRequest.getIsbn()) != null){
            throw new BookAlreadyExistException();
        }

        Book book = bookService.saveBook(bookMapper.bookRequestToBook(bookRequest),
                authorId, publisherId, genreId);

        return ResponseEntity.created(URI.create("/book/" + book.getBookId()))
                .body(book);
    }

    @Operation(summary = "Get book based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book information was successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "The book with the provided ID doesn't exist")
    })
    /* ====================
          Get book by ID
     ====================== */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        Optional<Book> book = bookService.findBookById(id);

        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(book);
    }

    @Operation(summary = "Get all books")
    /* ====================
          Get all books
    ======================= */
    @GetMapping("/list")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @Operation(summary = "Search book",
            description = "Returns all books containing the title provided")
    /* ====================
            Search book
     ====================== */
    @GetMapping("/search")
    public ResponseEntity<?> searchBook(@RequestBody String title){
        List<Book> booksList = bookService.searchBook(title);

        if (booksList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(booksList);
    }

}
