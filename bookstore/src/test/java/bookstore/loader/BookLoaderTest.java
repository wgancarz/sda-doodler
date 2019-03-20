package bookstore.loader;

import bookstore.model.Book;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookLoaderTest {

    @Test
    public void shouldReturnCorrectBook() {
        //GIVEN
        String title = "title";
        String author = "author";
        Integer releaseYear = 1992;
        Integer pageCount = 200;
        String publisher = "publisher";
        String category = "category";

        String data = String.join(";", title, author, releaseYear.toString(), pageCount.toString(), publisher, category);

        //WHEN
        Book book = BookLoader.parseBook(data);

        //THEN
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(releaseYear, book.getReleaseYear());
        assertEquals(pageCount, book.getPageCount());
        assertEquals(publisher, book.getPublisher());
        assertEquals(category, book.getCategory());
    }
}
