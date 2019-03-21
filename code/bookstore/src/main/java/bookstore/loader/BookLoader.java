package bookstore.loader;

import bookstore.model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookLoader {

    public static List<Book> loadBooks(String resourceName) {
        Path path;

        try {
            path = Paths.get(BookLoader.class.getClassLoader().getResource(resourceName).toURI());
        } catch (URISyntaxException e) {
            System.err.println("Incorrect file path.");
            e.printStackTrace();
            return null;
        }

        return loadBooks(path);
    }

    public static List<Book> loadBooks(Path path) {
        List<Book> books = new ArrayList<>();

        try {
            Files.lines(path).forEach(line -> books.add(parseBook(line)));
        } catch (IOException e) {
            System.err.println("I/O error during opening of input file.");
            e.printStackTrace();
            return null;
        }

        return books;
    }

    public static Book parseBook(String data) {
        String[] array = data.split(";");
        Arrays.setAll(array, i -> array[i].trim());

        String title = array[0];
        String author = array[1];
        Integer releaseYear = Integer.valueOf(array[2]);
        Integer pageCount = Integer.valueOf(array[3]);
        String publisher = array[4];
        String category = array[5];

        return new Book(title, author, releaseYear, pageCount, publisher, category);
    }
}
