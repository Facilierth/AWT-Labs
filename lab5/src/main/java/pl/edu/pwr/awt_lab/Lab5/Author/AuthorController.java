package pl.edu.pwr.awt_lab.Lab5.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pl.edu.pwr.awt_lab.Lab5.Book.IBooksService;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired private IAuthorService authorService;
    @Autowired private IBooksService booksService;

    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of authors retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Object> getAuthors() {
        return ResponseEntity.ok(authorService.getAuthors());
    }

    @Operation(summary = "Get a author by ID", description = "Retrieves a author by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthor(@Parameter(description = "ID of the author") @PathVariable int id) {
        Author author = authorService.getAuthor(id);
        return (author != null) ? ResponseEntity.ok(author) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
    }

    @Operation(summary = "Add a new author", description = "Creates a author by specifying name and nationality.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Object> addAuthor(AuthorCreateRequest request) {

        int newId = authorService.getAuthors().stream()
                           .mapToInt(Author::getId)
                           .max()
                           .orElse(0) + 1;
       
        Author author = new Author(newId, request.getName(), request.getNationality());
        authorService.addAuthor(author);

        return ResponseEntity.status(HttpStatus.CREATED).body("Author added successfully.");
    }


    @Operation(summary = "Update a author", description = "Updates an existing author by ID.")
    @ApiResponse(responseCode = "200", description = "Author updated successfully")
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable int id, @RequestBody AuthorCreateRequest author) {

        Author existingAuthor = authorService.getAuthor(id);
        if (existingAuthor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with ID " + id + " not found.");
        }

        existingAuthor.setName(author.getName());
        existingAuthor.setNationality(author.getNationality());
        
        return ResponseEntity.ok("Author updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable int id) {
        booksService.deleteBooksByAuthor(id);
        return authorService.deleteAuthor(id) ? ResponseEntity.ok("Author deleted.") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
    }
    
}
