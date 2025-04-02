package pl.edu.pwr.awt_lab.Lab5.Book;

// its just a ViewModel so its easier to add books
public class BookCreateRequest {
    private String title;
    private int pages;
    private int authorId;

    public BookCreateRequest(String title, int pages, int authorId) {
        this.title = title;
        this.pages = pages;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
