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
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public AdminView(PrintWriter outputStream, BufferedReader inputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.adminService = new AdminService();
    }



    public void operations() throws SocketException {
        try {
            int choice = -1;
            do {
                choice = Integer.parseInt(inputStream.readLine());

                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> searchBook();
                    case 4 -> viewBooks();
                    case 0 -> outputStream.println("\nExiting...");
                    default -> outputStream.println("\nInvalid Operation");
                }
            } while (choice != 0);
        }catch (SocketException exception) {
            throw exception;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void addBook() throws IOException {
        try {
            String ISBN = inputStream.readLine();

            String name = inputStream.readLine();

            String author = inputStream.readLine();

            String genre = inputStream.readLine();

            Book book = new Book(ISBN, name, author, genre);
            adminService.addBook(book);
            outputStream.println("Book added Successfully!");
        } catch (Exception exception) {
            outputStream.println(exception.getMessage());
        }

    }

    private void removeBook() throws IOException {
        try {
            String ISBN = inputStream.readLine();
            adminService.removeBook(ISBN);
            outputStream.println("Book removed Successfully!");
        } catch (Exception exception) {
            outputStream.println(exception.getMessage());
        }

    }

    private void searchBook() throws IOException {

        int searchChoice = Integer.parseInt(inputStream.readLine());

        switch (searchChoice) {
            case 1 -> getBookByName();
            case 2 -> getBookByAuthor();
            case 3 -> getBookByISBN();
            case 4 -> getBookByGenre();
            default -> outputStream.println("Invalid search option");
        }
    }

    private void getBookByName() throws IOException {
        String name = inputStream.readLine();
        List<Book> books = adminService.getBooksByName(name);
        sendBooks(books);
    }

    private void getBookByAuthor() throws IOException {
        String author = inputStream.readLine();
        List<Book> books = adminService.getBooksByAuthor(author);
        sendBooks(books);
    }

    private void getBookByISBN() throws IOException {
        String ISBN = inputStream.readLine();
        Book book = adminService.getBookByISBN(ISBN);
        if (book != null) {
            outputStream.println(book);
        } else {
            outputStream.println("Book not found with ISBN: " + ISBN);
        }
        outputStream.println("end");
    }

    private void getBookByGenre() throws IOException {
        String genre = inputStream.readLine();
        List<Book> books = adminService.getBooksByGenre(genre);
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
        List<Book> books = adminService.getAllBooks();
        if (books.isEmpty()) {
            outputStream.println("No books available.");
        } else {
            for (Book book : books) {
                outputStream.println(book);
            }
        }
        outputStream.println("end");
    }
}
