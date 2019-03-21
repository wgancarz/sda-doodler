package bookstore;

import bookstore.loader.BookLoader;
import bookstore.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Book> books = BookLoader.loadBooks("books.csv");

        List<String> lambdaTitles = getBooksAboutPythonFrom2016OrOlderUsingLambda(books);
        printBooksUsingLambda(lambdaTitles);

        List<String> forEachTitles = getBooksAboutPythonFrom2016OrOlderUsingForEach(books);
        printBooksUsingForEach(forEachTitles);

        System.out.println("Are the sets identical? " + lambdaTitles.equals(forEachTitles));
    }

    private static void printBooksUsingForEach(List<String> forEachTitles) {
        for (String title : forEachTitles) {
            System.out.println(title);
        }
    }

    private static void printBooksUsingLambda(List<String> lambdaTitles) {
        lambdaTitles.forEach(System.out::println);
    }

    private static List<String> getBooksAboutPythonFrom2016OrOlderUsingForEach(List<Book> books) {
        List<String> titles = new ArrayList<>();

        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase("Python")
                    && (book.getReleaseYear() <= 2016)) {
                titles.add(book.getTitle());
            }
        }

        return titles;
    }

    private static List<String> getBooksAboutPythonFrom2016OrOlderUsingLambda(List<Book> books) {
        List<String> titles = books.stream()
                .filter(b -> b.getCategory().equalsIgnoreCase("Python"))
                .filter(b -> b.getReleaseYear() <= 2016)
                .map(b -> b.getTitle())
                .collect(Collectors.toList());

        return titles;
    }
}
