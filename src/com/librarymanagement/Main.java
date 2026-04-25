package com.librarymanagement;

import com.librarymanagement.factory.BookFactory;
import com.librarymanagement.factory.PatronFactory;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.LoanRecord;
import com.librarymanagement.observer.AuditLogObserver;
import com.librarymanagement.observer.InventoryLogObserver;
import com.librarymanagement.observer.LendingEventPublisher;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.InMemoryBookRepository;
import com.librarymanagement.repository.InMemoryLoanRepository;
import com.librarymanagement.repository.InMemoryPatronRepository;
import com.librarymanagement.repository.LoanRepository;
import com.librarymanagement.repository.PatronRepository;
import com.librarymanagement.search.BookSearchStrategyFactory;
import com.librarymanagement.search.SearchType;
import com.librarymanagement.service.BookService;
import com.librarymanagement.service.BookServiceImpl;
import com.librarymanagement.service.LendingService;
import com.librarymanagement.service.LendingServiceImpl;
import com.librarymanagement.service.LibraryFacade;
import com.librarymanagement.service.PatronService;
import com.librarymanagement.service.PatronServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryFacade library = buildLibrary();
        runCli(library);
    }

    private static void runCli(LibraryFacade library) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = readInt(scanner, "Choose an option: ");

                try {
                    switch (choice) {
                        case 1:
                            addBook(scanner, library);
                            break;
                        case 2:
                            updateBook(scanner, library);
                            break;
                        case 3:
                            removeBook(scanner, library);
                            break;
                        case 4:
                            searchBooks(scanner, library);
                            break;
                        case 5:
                            listAllBooks(library);
                            break;
                        case 6:
                            addPatron(scanner, library);
                            break;
                        case 7:
                            updatePatron(scanner, library);
                            break;
                        case 8:
                            checkoutBook(scanner, library);
                            break;
                        case 9:
                            returnBook(scanner, library);
                            break;
                        case 10:
                            viewPatronHistory(scanner, library);
                            break;
                        case 11:
                            seedSampleData(library);
                            break;
                        case 0:
                            running = false;
                            System.out.println("Goodbye.");
                            break;
                        default:
                            System.out.println("Invalid option. Try again.");
                    }
                } catch (RuntimeException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("=== Library Management System ===");
        System.out.println("1. Add book");
        System.out.println("2. Update book");
        System.out.println("3. Remove book");
        System.out.println("4. Search books");
        System.out.println("5. List all books");
        System.out.println("6. Add patron");
        System.out.println("7. Update patron");
        System.out.println("8. Checkout book");
        System.out.println("9. Return book");
        System.out.println("10. View patron borrowing history");
        System.out.println("11. Seed sample data");
        System.out.println("0. Exit");
    }

    private static void addBook(Scanner scanner, LibraryFacade library) {
        String isbn = readLine(scanner, "ISBN: ");
        String title = readLine(scanner, "Title: ");
        String author = readLine(scanner, "Author: ");
        int year = readInt(scanner, "Publication year: ");
        library.books().addBook(isbn, title, author, year);
        System.out.println("Book added.");
    }

    private static void updateBook(Scanner scanner, LibraryFacade library) {
        String isbn = readLine(scanner, "ISBN of book to update: ");
        String title = readLine(scanner, "New title: ");
        String author = readLine(scanner, "New author: ");
        int year = readInt(scanner, "New publication year: ");
        library.books().updateBook(isbn, title, author, year);
        System.out.println("Book updated.");
    }

    private static void removeBook(Scanner scanner, LibraryFacade library) {
        String isbn = readLine(scanner, "ISBN to remove: ");
        boolean removed = library.books().removeBook(isbn);
        System.out.println(removed ? "Book removed." : "Book not found.");
    }

    private static void searchBooks(Scanner scanner, LibraryFacade library) {
        System.out.println("Search by: 1) Title 2) Author 3) ISBN");
        int mode = readInt(scanner, "Choose search mode: ");
        SearchType searchType;
        switch (mode) {
            case 1:
                searchType = SearchType.TITLE;
                break;
            case 2:
                searchType = SearchType.AUTHOR;
                break;
            case 3:
                searchType = SearchType.ISBN;
                break;
            default:
                System.out.println("Invalid search mode.");
                return;
        }

        String query = readLine(scanner, "Query: ");
        List<Book> books = library.books().searchBooks(searchType, query);
        printBooks(books);
    }

    private static void listAllBooks(LibraryFacade library) {
        printBooks(library.books().getAllBooks());
    }

    private static void addPatron(Scanner scanner, LibraryFacade library) {
        String patronId = readLine(scanner, "Patron ID: ");
        String name = readLine(scanner, "Name: ");
        String email = readLine(scanner, "Email: ");
        library.patrons().addPatron(patronId, name, email);
        System.out.println("Patron added.");
    }

    private static void updatePatron(Scanner scanner, LibraryFacade library) {
        String patronId = readLine(scanner, "Patron ID to update: ");
        String name = readLine(scanner, "New name: ");
        String email = readLine(scanner, "New email: ");
        library.patrons().updatePatron(patronId, name, email);
        System.out.println("Patron updated.");
    }

    private static void checkoutBook(Scanner scanner, LibraryFacade library) {
        String patronId = readLine(scanner, "Patron ID: ");
        String isbn = readLine(scanner, "ISBN: ");
        LoanRecord loan = library.lending().checkoutBook(patronId, isbn);
        System.out.println("Checked out. Due date: " + loan.getDueDate());
    }

    private static void returnBook(Scanner scanner, LibraryFacade library) {
        String patronId = readLine(scanner, "Patron ID: ");
        String isbn = readLine(scanner, "ISBN: ");
        library.lending().returnBook(patronId, isbn);
        System.out.println("Book returned.");
    }

    private static void viewPatronHistory(Scanner scanner, LibraryFacade library) {
        String patronId = readLine(scanner, "Patron ID: ");
        List<LoanRecord> history = library.patrons().getBorrowingHistory(patronId);
        if (history.isEmpty()) {
            System.out.println("No borrowing history found.");
            return;
        }

        for (LoanRecord loan : history) {
            String returnDate = loan.getReturnDate() == null ? "ACTIVE" : loan.getReturnDate().toString();
            System.out.println(String.format(
                    "ISBN=%s, checkout=%s, due=%s, return=%s",
                    loan.getIsbn(), loan.getCheckoutDate(), loan.getDueDate(), returnDate));
        }
    }

    private static void seedSampleData(LibraryFacade library) {
        library.books().addBook("978-1", "Clean Code", "Robert Martin", 2008);
        library.books().addBook("978-2", "Effective Java", "Joshua Bloch", 2018);
        library.books().addBook("978-3", "Design Patterns", "GoF", 1994);
        library.patrons().addPatron("P1", "Rohan", "rohan@example.com");
        System.out.println("Sample data seeded.");
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book book : books) {
            System.out.println(String.format(
                    "ISBN=%s, title=%s, author=%s, year=%d, status=%s",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getStatus()));
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LibraryFacade buildLibrary() {
        BookRepository bookRepository = new InMemoryBookRepository();
        PatronRepository patronRepository = new InMemoryPatronRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        BookService bookService = new BookServiceImpl(
                bookRepository,
                new BookFactory(),
                new BookSearchStrategyFactory());

        PatronService patronService = new PatronServiceImpl(
                patronRepository,
                new PatronFactory(),
                loanRepository);

        LendingEventPublisher eventPublisher = new LendingEventPublisher();
        eventPublisher.registerObserver(new AuditLogObserver());
        eventPublisher.registerObserver(new InventoryLogObserver(bookRepository));

        LendingService lendingService = new LendingServiceImpl(
                bookRepository,
                patronRepository,
                loanRepository,
                eventPublisher);

        return new LibraryFacade(bookService, patronService, lendingService);
    }
}
