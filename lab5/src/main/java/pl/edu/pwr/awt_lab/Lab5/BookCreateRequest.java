package pl.edu.pwr.awt_lab.Lab5;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookCreateRequest {
    @Schema(description = "Title of the book", example = "Lalka", required = true)
    private String title;

    @Schema(description = "Number of pages", example = "520", required = true)
    private int pages;

    @Schema(description = "ID of the author", example = "1", required = true)
    private int authorId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
}
