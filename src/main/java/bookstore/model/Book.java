package bookstore.model;

public class Book {
    String title;
    String author;
    Integer releaseYear;
    Integer pageCount;
    String publisher;
    String category;

    public Book() {

    }

    public Book(String title, String author, Integer releaseYear, Integer pageCount, String publisher, String category) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.category = category;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Title: " + title + "\n");
        sb.append("Author: " + author + "\n");
        sb.append("Release year: " + releaseYear + "\n");
        sb.append("Page count: " + pageCount + "\n");
        sb.append("Publisher: " + publisher + "\n");
        sb.append("Category: " + category + "\n");

        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
