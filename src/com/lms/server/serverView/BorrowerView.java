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
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private Borrower borrower;

    public BorrowerView(Borrower borrower, PrintWriter outputStream, BufferedReader inputStream) {
        this.borrower = borrower;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.borrowerService = new BorrowerService(borrower);
    }

    public void operations() throws SocketException {
        try {
            int choice = -1;
            do {

                choice = Integer.parseInt(inputStream.readLine());
                switch (choice) {
                    case 1 -> issueBook();
                    case 2 -> returnBook();
                    case 3 -> searchBook();
                    case 4 -> viewBooks();
                    case 5 -> getBorrowedBooks();
                }
            } while (choice != 0);
        } catch (SocketException exception) {
            throw exception;
        } catch (Exception exception) {
            outputStream.println("An error occurred: " + exception.getMessage());
        }
    }

    private void issueBook() throws IOException {
        String ISBN = inputStream.readLine();
        try {
            if (borrowerService.issueBook(ISBN)) {
                outputStream.println("Book Issued Successfully!");
            } else {
                outputStream.println("Could not issue Book. It might be unavailable or already borrowed.");
            }
        } catch (Exception exception) {
            outputStream.println("Error issuing book: " + exception.getMessage());
        }
    }

    private void returnBook() throws IOException {

        String ISBN = inputStream.readLine();
        try {
            long penalty = borrowerService.returnBook(ISBN);
            outputStream.println("Book returned Successfully!");
            if (penalty > 0) {
                outputStream.println("You have a penalty of: " + penalty + " for late return.");
            }
        } catch (Exception exception) {
            outputStream.println("Error returning book: " + exception.getMessage());
        }
    }

    private void searchBook() throws IOException {

        int searchChoice = Integer.parseInt(inputStream.readLine());

        switch (searchChoice) {
            case 1 -> getBookByName();
            case 2 -> getBookByAuthor();
            case 3 -> getBookByISBN();
            case 4 -> getBookByGenre();
        }
    }

    private void getBookByName() throws IOException {
        String name = inputStream.readLine();
        List<Book> books = borrowerService.getBooksByName(name);
        sendBooks(books);
    }

    private void getBookByAuthor() throws IOException {
        String author = inputStream.readLine();
        List<Book> books = borrowerService.getBooksByAuthor(author);
        sendBooks(books);
    }

    private void getBookByISBN() throws IOException {
        String ISBN = inputStream.readLine();
        Book book = borrowerService.getBookByISBN(ISBN);
        if (book != null) {
            outputStream.println(book);
        } else {
            outputStream.println("Book not found with ISBN: " + ISBN);
        }
        outputStream.println("end");
    }

    private void getBookByGenre() throws IOException {
        String genre = inputStream.readLine();
        List<Book> books = borrowerService.getBooksByGenre(genre);
        sendBooks(books);
    }

    private void sendBooks(List<Book> books) {
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                outputStream.println(book);
            }
        } else {
            outputStream.println("No books found.");
        }
        outputStream.println("end");
    }

    private void viewBooks() {
        List<Book> books = borrowerService.getAllBooks();
       sendBooks(books);
    }

    private void getBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowerService.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            outputStream.println("You have not borrowed any books.");
        } else {
            for (BorrowedBook book : borrowedBooks) {
                outputStream.println(book);
            }
        }
        outputStream.println("end");
    }
}
