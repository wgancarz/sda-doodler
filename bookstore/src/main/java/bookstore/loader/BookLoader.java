package bookstore.loader;

import bookstore.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BookLoader {

    public static List<Book> loadBooks(String pathString) {
        return loadBooks(Paths.get(pathString));
    }

    public static List<Book> loadBooks(Path path) {
        List<Book> books = new ArrayList<>();

        try {
            Files.lines(path).forEach(line -> books.add(parseBook(line)));
        } catch (IOException e) {
            System.err.println("Error reading input file.");
            e.printStackTrace();
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
