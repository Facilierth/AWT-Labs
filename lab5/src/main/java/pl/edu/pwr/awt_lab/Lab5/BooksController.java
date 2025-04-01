package pl.edu.pwr.awt_lab.Lab5;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private IBooksService booksService;

    @Autowired private IAuthorService authorService;

    @Operation(summary = "Get all books", description = "Retrieves a list of all books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Object> getBooks() {
        return ResponseEntity.ok(booksService.getBooks());
    }

    @Operation(summary = "Get a book by ID", description = "Retrieves a book by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@Parameter(description = "ID of the book") @PathVariable int id) {
        Book book = booksService.getBook(id);
        return (book != null) ? ResponseEntity.ok(book) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    @Operation(summary = "Add a new book", description = "Creates a book by specifying title, pages, and author ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Object> addBook(
    BookCreateRequest request) {
        int authorId = request.getAuthorId();
        System.out.println("Hi" + authorId);
        Author author = authorService.getAuthor(authorId);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author with ID " + request.getAuthorId() + " not found.");
        }

        Book book = new Book(booksService.getBooks().size() + 1, request.getTitle(), author, request.getPages());
        booksService.addBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully.");
    }


    @Operation(summary = "Update a book", description = "Updates an existing book by ID.")
    @ApiResponse(responseCode = "200", description = "Book updated successfully")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable int id, @RequestBody BookCreateRequest book) {

        Book existingBook = booksService.getBook(id);
        if (existingBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + id + " not found.");
        }

        Author author = authorService.getAuthor(book.getAuthorId());
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author with ID " + book.getAuthorId() + " not found.");
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setPages(book.getPages());
        existingBook.setAuthor(author);

        return ResponseEntity.ok("Book updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable int id) {
        return booksService.deleteBook(id) ? ResponseEntity.ok("Book deleted.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }
}
