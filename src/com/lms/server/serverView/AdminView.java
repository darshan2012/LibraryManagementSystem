package com.lms.server.serverView;

import com.lms.server.models.Book;
import com.lms.server.services.AdminService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.List;

public class AdminView {
    private AdminService adminService;
    private BufferedReader inStream;
    private PrintWriter outStream;

    public AdminView(PrintWriter outStream, BufferedReader inStream) {
        this.inStream = inStream;
        this.outStream = outStream;
        this.adminService = new AdminService();
    }



    public void operations() throws SocketException {
        try {
            int choice = -1;
            do {
                choice = Integer.parseInt(inStream.readLine());

                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> searchBook();
                    case 4 -> viewBooks();
                    case 0 -> outStream.println("\nExiting...");
                    default -> outStream.println("\nInvalid Operation");
                }
            } while (choice != 0);
        }catch (SocketException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook() throws IOException {
        try {
            String ISBN = inStream.readLine();

            String name = inStream.readLine();

            String author = inStream.readLine();

            String genre = inStream.readLine();

            Book book = new Book(ISBN, name, author, genre);
            adminService.addBook(book);
            outStream.println("Book added Successfully!");
        } catch (Exception e) {
            outStream.println(e.getMessage());
        }

    }

    private void removeBook() throws IOException {
        try {
            String ISBN = inStream.readLine();
            adminService.removeBook(ISBN);
            outStream.println("Book removed Successfully!");
        } catch (Exception e) {
            outStream.println(e.getMessage());
        }

    }

    private void searchBook() throws IOException {

        int searchChoice = Integer.parseInt(inStream.readLine());

        switch (searchChoice) {
            case 1 -> handleSearchBookByName();
            case 2 -> handleSearchBookByAuthor();
            case 3 -> handleSearchBookByISBN();
            case 4 -> handleSearchBookByGenre();
            default -> outStream.println("Invalid search option");
        }
    }

    private void handleSearchBookByName() throws IOException {
        String name = inStream.readLine();
        List<Book> books = adminService.searchBooksByName(name);
        sendBooks(books);
    }

    private void handleSearchBookByAuthor() throws IOException {
        String author = inStream.readLine();
        List<Book> books = adminService.searchBooksByAuthor(author);
        sendBooks(books);
    }

    private void handleSearchBookByISBN() throws IOException {
        String ISBN = inStream.readLine();
        Book book = adminService.getBookByISBN(ISBN);
        if (book != null) {
            outStream.println(book);
        } else {
            outStream.println("Book not found with ISBN: " + ISBN);
        }
        outStream.println("end");
    }

    private void handleSearchBookByGenre() throws IOException {
        String genre = inStream.readLine();
        List<Book> books = adminService.getBooksByGenre(genre);
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
        List<Book> books = adminService.getAllBooks();
        if (books.isEmpty()) {
            outStream.println("No books available.");
        } else {
            for (Book book : books) {
                outStream.println(book);
            }
        }
        outStream.println("end");
    }
}
