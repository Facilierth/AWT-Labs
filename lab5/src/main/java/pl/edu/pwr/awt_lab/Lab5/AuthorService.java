package pl.edu.pwr.awt_lab.Lab5;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    private static List<Author> authorsRepo = new ArrayList<>();

    static {
        Author sienkiewicz = new Author(1, "Henryk Sienkiewicz", "Poland");
        Author reymont = new Author(2, "Stanisław Reymont", "Poland");
        Author mickiewicz = new Author(3, "Adam Mickiewicz", "Poland");
        authorsRepo.addAll(List.of(sienkiewicz, reymont, mickiewicz));
    }

    @Override
    public Collection<Author> getAuthors() { return authorsRepo; }

    @Override
    public Author getAuthor(int id) {
        return authorsRepo.stream().filter(a -> a.getId() == id).findAny().orElse(null);
    }

    @Override
    public void addAuthor(Author author) { authorsRepo.add(author); }

    @Override
    public void updateAuthor(int id, Author updatedAuthor) {
        authorsRepo.replaceAll(author -> (author.getId() == id) ? updatedAuthor : author);
    }

    @Override
    public boolean deleteAuthor(int id) {
        return authorsRepo.removeIf(author -> author.getId() == id);
    }
}
