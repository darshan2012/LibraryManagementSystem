package com.lms.server.serverView;

import com.lms.server.models.Book;
import com.lms.server.models.BorrowedBook;
import com.lms.server.models.Borrower;
import com.lms.server.services.BorrowerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.List;

public class BorrowerView {
    private BorrowerService borrowerService;
    private BufferedReader inStream;
    private PrintWriter outStream;
    private Borrower borrower;

    public BorrowerView(Borrower borrower, PrintWriter outStream, BufferedReader inStream) {
        this.borrower = borrower;
        this.inStream = inStream;
        this.outStream = outStream;
        this.borrowerService = new BorrowerService(borrower);
    }
    

    public void operations() throws SocketException {
        try {
            int choice = -1;
            do {

                choice = Integer.parseInt(inStream.readLine());
                switch (choice) {
                    case 1 -> issueBook();
                    case 2 -> returnBook();
                    case 3 -> searchBook();
                    case 4 -> viewBooks();
                    case 5 -> getBorrowedBooks();
                }
            } while (choice != 0);
        } catch (SocketException e) {
            throw e;
        } catch (Exception e) {
            outStream.println("An error occurred: " + e.getMessage());
        }
    }

    private void issueBook() throws IOException {
        String ISBN = inStream.readLine();
        try {
            if (borrowerService.issueBook(ISBN)) {
                outStream.println("Book Issued Successfully!");
            } else {
                outStream.println("Could not issue Book. It might be unavailable or already borrowed.");
            }
        } catch (Exception e) {
            outStream.println("Error issuing book: " + e.getMessage());
        }
    }

    private void returnBook() throws IOException {

        String ISBN = inStream.readLine();
        try {
            long penalty = borrowerService.returnBook(ISBN);
            outStream.println("Book returned Successfully!");
            if (penalty > 0) {
                outStream.println("You have a penalty of: " + penalty + " for late return.");
            }
        } catch (Exception e) {
            outStream.println("Error returning book: " + e.getMessage());
        }
    }

    private void searchBook() throws IOException {

        int searchChoice = Integer.parseInt(inStream.readLine());

        switch (searchChoice) {
            case 1 -> handleSearchBookByName();
            case 2 -> handleSearchBookByAuthor();
            case 3 -> handleSearchBookByISBN();
            case 4 -> handleSearchBookByGenre();
        }
    }

    private void handleSearchBookByName() throws IOException {
        String name = inStream.readLine();
        List<Book> books = borrowerService.searchBooksByName(name);
        sendBooks(books);
    }

    private void handleSearchBookByAuthor() throws IOException {
        String author = inStream.readLine();
        List<Book> books = borrowerService.searchBooksByAuthor(author);
        sendBooks(books);
    }

    private void handleSearchBookByISBN() throws IOException {
        String ISBN = inStream.readLine();
        Book book = borrowerService.getBookByISBN(ISBN);
        if (book != null) {
            outStream.println(book);
        } else {
            outStream.println("Book not found with ISBN: " + ISBN);
        }
        outStream.println("end");
    }

    private void handleSearchBookByGenre() throws IOException {
        String genre = inStream.readLine();
        List<Book> books = borrowerService.getBooksByGenre(genre);
        sendBooks(books);
    }

    private void sendBooks(List<Book> books) {
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                outStream.println(book);
            }
        } else {
            outStream.println("No books found.");
        }
        outStream.println("end");
    }

    private void viewBooks() {
        List<Book> books = borrowerService.getAllBooks();
       sendBooks(books);
    }

    private void getBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowerService.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            outStream.println("You have not borrowed any books.");
        } else {
            for (BorrowedBook book : borrowedBooks) {
                outStream.println(book);
            }
        }
        outStream.println("end");
    }
}
