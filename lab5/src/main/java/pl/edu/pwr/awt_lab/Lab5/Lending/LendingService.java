package pl.edu.pwr.awt_lab.Lab5.Lending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.awt_lab.Lab5.Book.Book;
import pl.edu.pwr.awt_lab.Lab5.Book.IBooksService;

@Service
public class LendingService implements ILendingService{
    

    @Autowired IBooksService booksService;

    @Override
    public void lentBook(int bookId) {
        Book book = booksService.getBook(bookId);
        book.setLentOut(true);
        booksService.updateBook(bookId, book);
    }

    @Override
    public void retrieveBook(int bookId) {
        Book book = booksService.getBook(bookId);
        book.setLentOut(true);
        booksService.updateBook(bookId, book);
    }


}
