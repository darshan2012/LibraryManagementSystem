package com.lms.client;

import com.lms.server.models.Borrower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class LMS {
    private BufferedReader inputReader;
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public LMS(String host, int port) throws IOException {
        inputReader = new BufferedReader(new InputStreamReader(System.in));
        socket = new Socket(host, port);
        outputStream = new PrintWriter(socket.getOutputStream(), true);
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(inputStream.readLine());
    }

    public void authChoices() {
        System.out.println("\n1. Login\n2. Registration\n3. Exit\n");
    }

    private void displayHomeHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("+-------------------------------------------+");
        System.out.println("|                                           |");
        System.out.println("| ======> Library Management System <====== |");
        System.out.println("|                                           |");
        System.out.println("+-------------------------------------------+");
        System.out.println();
    }

    public void start() {
        displayHomeHeader();

        int choice = 0;
        do {
            try {
                authChoices();
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(inputReader.readLine());
                outputStream.println(choice);
                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 555 -> adminLogin();
                    case 3 -> System.out.println("Exiting...");
                    default -> System.err.println("Invalid choice!");
                }
            } catch (SocketException exception) {
                System.out.println("Socket Exception.." + exception.getMessage());
                break;
            } catch (Exception exception) {
                System.err.println("Invalid choice!");
            }
        } while (choice != 3);

        try {
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void login() {
        try {
            System.out.print("Enter username: ");
            String username = inputReader.readLine();
            outputStream.println(username);
            System.out.print("Enter password: ");
            String password = inputReader.readLine();
            outputStream.println(password);

            String response = inputStream.readLine();
            if (response.equals("Authenticated")) {
                System.out.println("\n\tLogin Successful!");
                Borrower borrower = new Borrower(username, password);
                BorrowerView borrowerView = new BorrowerView(borrower, outputStream, inputStream,inputReader);
                borrowerView.operations();
            } else {
                System.err.println("Authentication failed! " + response);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void register() {
        try {
            System.out.print("Enter username: ");
            String username = inputReader.readLine();
            outputStream.println(username);
            System.out.print("Enter password: ");
            String password = inputReader.readLine();
            outputStream.println(password);
            System.out.print("Enter name: ");
            String name = inputReader.readLine();
            outputStream.println(name);

            String response = inputStream.readLine();
            if (response.equals("Registered")) {
                System.out.println("Registration Successful! Please login.");
            } else {
                System.err.println("Registration failed! : " + response);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void adminLogin() {

        String response = "Authenticated";
        if (response.equals("Authenticated")) {
            AdminView adminView = new AdminView(inputStream, outputStream,inputReader);
            adminView.operations();
        } else {
            System.err.println("Authentication failed! : " + response);
        }
    }

    public static void main(String[] args) {
        try {
            LMS lms = new LMS("localhost", 6666);
            lms.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
