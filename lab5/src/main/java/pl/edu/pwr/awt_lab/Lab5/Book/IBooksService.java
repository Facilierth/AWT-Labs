package pl.edu.pwr.awt_lab.Lab5.Book;
import java.util.Collection;
public interface IBooksService {
    public abstract Collection<Book> getBooks();

    public abstract Book getBook(int id);
    public abstract void addBook(Book book);
    public abstract void updateBook(int id, Book book);
    public abstract boolean deleteBook(int id);
}