package pl.edu.pwr.awt_lab.Lab5.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.awt_lab.Lab5.Author.Author;
import pl.edu.pwr.awt_lab.Lab5.Author.AuthorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BooksService implements IBooksService {
    private static List<Book> booksRepo = new ArrayList<>();
    private final AuthorService authorService;

    @Autowired
    public BooksService(AuthorService authorService) {
        this.authorService = authorService;
        initializeBooks();
    }

    private void initializeBooks() {
        Collection<Author> authors = authorService.getAuthors();
        Author sienkiewicz = authorService.getAuthor(1);
        Author reymont = authorService.getAuthor(2);
        Author mickiewicz = authorService.getAuthor(3);

        booksRepo.add(new Book(1, "Potop", sienkiewicz, 936));
        booksRepo.add(new Book(2, "Wesele", reymont, 150));
        booksRepo.add(new Book(3, "Dziady", mickiewicz, 292));
    }
    @Override
    public Collection<Book> getBooks() { return booksRepo; }

    @Override
    public Book getBook(int id) {
        return booksRepo.stream().filter(b -> b.getId() == id).findAny().orElse(null);
    }

    @Override
    public void addBook(Book book) { booksRepo.add(book); }

    @Override
    public void updateBook(int id, Book updatedBook) {
        booksRepo.replaceAll(book -> (book.getId() == id) ? updatedBook : book);
    }

    @Override
    public boolean deleteBook(int id) {
        return booksRepo.removeIf(book -> book.getId() == id);
    }

    @Override
    public void deleteBooksByAuthor(int authorId) {
        booksRepo.removeIf(book -> book.getAuthor().getId() == authorId);
    }

}
