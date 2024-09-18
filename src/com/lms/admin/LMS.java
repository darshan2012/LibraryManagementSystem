package com.lms.admin;

import com.lms.client.BorrowerView;
import com.lms.server.models.Borrower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class LMS
{
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
        System.out.println("\n1. Login\n2. Exit\n");
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
                if(choice == 2)
                {
                    choice = 3;
                }else if(choice == 1)
                    choice = 555;
                outputStream.println(choice);
                switch (choice) {
                    case 555 -> adminLogin();
                    case 3 -> System.out.println("Exiting...");
                    default -> System.err.println("Invalid choice!");
                }
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

    private void adminLogin() {
        try
        {
            System.out.print("Enter username: ");
            String username = inputReader.readLine();
            outputStream.println(username);
            System.out.print("Enter password: ");
            String password = inputReader.readLine();
            outputStream.println(password);
            String response = inputStream.readLine();
            if (response.equals("Authenticated")) {
                AdminView adminView = new AdminView(inputStream, outputStream,inputReader);
                adminView.operations();
            } else {
                System.err.println("Authentication failed! : " + response);
            }
        } catch (Exception exception)
        {
            System.err.println("Could not login");
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
