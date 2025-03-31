package pl.edu.pwr.awt_lab.Lab5;
import java.util.Collection;
public interface IBooksService {
    public abstract Collection<Book> getBooks();
    public abstract Book getBook(int id);
}