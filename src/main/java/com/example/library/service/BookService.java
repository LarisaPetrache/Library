package com.example.library.service;

import com.example.library.exception.BookAlreadyExistException;
import com.example.library.models.Author;
import com.example.library.models.Book;
import com.example.library.models.Genre;
import com.example.library.models.Publisher;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;
import com.example.library.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository,
                       PublisherRepository publisherRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    public Book saveBook(Book book, int authorId, int publisherId, int genreId) throws BookAlreadyExistException {

        // Throw exception if author, publisher and genre IDs are not valid
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author id is not valid"));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher id is not valid"));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre id is not valid"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenre(genre);

        return bookRepository.save(book);
    }

    public Optional<Book> findBookById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBook(String title){
        return bookRepository.findBookByTitleContainingIgnoreCase(title);
    }

    public Book findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn);
    }
}
