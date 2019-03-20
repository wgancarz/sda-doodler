package bookstore;

import bookstore.loader.BookLoader;
import bookstore.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Book> books = BookLoader.loadBooks("/Users/i323626/Downloads/BookApp/dane.txt");

        // wyjmij liste tytulow ksiazek o pythonie z roku 2016 i starszych

        // LAMBDA
        List<String> lambdaTitles = books.stream()
                .filter(b -> b.getCategory().equalsIgnoreCase("Python"))
                .filter(b -> b.getReleaseYear() <= 2016)
                .map(b -> b.getTitle())
                .collect(Collectors.toList());

        lambdaTitles.forEach(System.out::println);

        // FOREACH
        List<String> forEachTitles = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase("Python")
                    && (book.getReleaseYear() <= 2016)) {
                forEachTitles.add(book.getTitle());
            }
        }

        for (String title : forEachTitles) {
            System.out.println(title);
        }

        // sprawdz czy wyszlo to samo
        System.out.println("IDENTICAL? " + lambdaTitles.equals(forEachTitles));
    }
}
