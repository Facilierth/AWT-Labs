package pl.edu.pwr.awt_lab.Lab5.Lending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import pl.edu.pwr.awt_lab.Lab5.Book.Book;
import pl.edu.pwr.awt_lab.Lab5.Book.IBooksService;

@RestController
@RequestMapping("/lending")
public class LendingController {
    
    @Autowired ILendingService lendingService;
    @Autowired IBooksService booksService;

    @Operation(summary = "Lent a book", description = "Changes lent status of an existing book by ID to true.")
    @ApiResponse(responseCode = "200", description = "Book lented successfully")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> lentBook(@PathVariable int id) {

        Book book = booksService.getBook(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + id + " not found.");
        }

        if (book.isLentOut()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with ID " + book.getId() + " is already lent out.");
        }

        lendingService.lentBook(id);

        return ResponseEntity.ok("Book lented successfully.");
    }

    @Operation(summary = "Retrieve a book", description = "Changes lent status of an existing book by ID to false.")
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> retrieveBook(@PathVariable int id) {

        Book book = booksService.getBook(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + id + " not found.");
        }

        if (!book.isLentOut()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with ID " + book.getId() + " is not currently lent out.");
        }

        lendingService.lentBook(id);

        return ResponseEntity.ok("Book retrieved successfully.");
    }

}
