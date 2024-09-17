package com.lms.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminView {
    private PrintWriter outStream;
    private BufferedReader inStream;
    private BufferedReader inputReader;

    public AdminView(BufferedReader inStream, PrintWriter outStream,BufferedReader inputReader) {
        this.inStream = inStream;
        this.outStream = outStream;
        this.inputReader = inputReader;
    }

    private void choices() {
        System.out.println("1. Add Book\n2. Remove Book\n3. Search Books\n4. View Books\n0. Exit");
        System.out.print("Please choose an Operation: ");
    }

    public void operations() {
        try {
            int choice = -1;
            do {
                choices();
                choice = Integer.parseInt(inputReader.readLine());
                outStream.println(choice);
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> removeBook();
                    case 3 -> searchBook();
                    case 4 -> viewBooks();
                    case 0 -> System.out.println("\nExiting...");
                    default -> System.err.println("\nInvalid Operation");
                }
            } while (choice != 0);
        } catch (Exception e) {
            System.err.println("Something went wrong...");
            e.printStackTrace();
        }
    }

    private void addBook() {
        try {
            System.out.print("Enter ISBN: ");
            String ISBN = inputReader.readLine();
            System.out.print("Enter Book Name: ");
            String name = inputReader.readLine();
            System.out.print("Enter Author Name: ");
            String author = inputReader.readLine();
            System.out.print("Enter Genre: ");
            String genre = inputReader.readLine();

            outStream.println(ISBN);
            outStream.println(name);
            outStream.println(author);
            outStream.println(genre);

            String response = inStream.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeBook() {
        try {
            System.out.print("Enter ISBN: ");
            String ISBN = inputReader.readLine();

            outStream.println(ISBN);

            String response = inStream.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchBook() {
        try {
            System.out.println("\n1. Search by Name\n2. Search by Author\n3. Search by ISBN\n4. Search by Genre\n0. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(inputReader.readLine());
            outStream.println(choice);
            switch (choice) {
                case 1 -> searchByName();
                case 2 -> searchByAuthor();
                case 3 -> searchByISBN();
                case 4 -> searchByGenre();
                case 0 -> System.out.println("\nExiting search...");
                default -> System.err.println("\nInvalid Operation");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByName() throws IOException {
        System.out.print("Enter book name: ");
        String name = inputReader.readLine();

        outStream.println(name);

        String response;
        while (!(response = inStream.readLine()).equals("end")) {
            System.out.println(response);
        }
    }

    private void searchByAuthor() throws IOException {
        System.out.print("Enter author name: ");
        String author = inputReader.readLine();

        outStream.println(author);

        String response;
        while (!(response = inStream.readLine()).equals("end")) {
            System.out.println(response);
        }
    }

    private void searchByISBN() throws IOException {
        System.out.print("Enter ISBN: ");
        String ISBN = inputReader.readLine();

        outStream.println(ISBN);

        String response = inStream.readLine();
        System.out.println(response);
    }

    private void searchByGenre() throws IOException {
        System.out.print("Enter genre: ");
        String genre = inputReader.readLine();
        outStream.println(genre);
        String response;
        while (!(response = inStream.readLine()).equals("end")) {
            System.out.println(response);
        }
    }

    private void viewBooks() {
        try {
            String response;
            while (!(response = inStream.readLine()).equals("end")) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
