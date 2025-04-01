package pl.edu.pwr.awt_lab.Lab5;

import java.util.Collection;

public interface IAuthorService {
    Collection<Author> getAuthors();
    public abstract Author getAuthor(int id);
    public abstract void addAuthor(Author author);
    public abstract void updateAuthor(int id, Author author);
    public abstract boolean deleteAuthor(int id);
}
