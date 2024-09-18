package com.lms.server.serverView;

import com.lms.server.models.Borrower;
import com.lms.server.services.AuthenticationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

public class LMS {
    private static LMS lms;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public LMS(PrintWriter outputStream, BufferedReader inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;

    }
    public void start(){

        int choice=0;

            try{
                do{
                choice = Integer.parseInt(inputStream.readLine());

                switch (choice){
                    case 1 -> login();
                    case 2 -> register();
                    case 555-> adminLogin();
                    case 3 -> System.out.println("Exiting...");
                    default -> System.err.println("Invalid choice!");
                }
                }while(choice != 3);
            }
            catch (SocketException exception)
            {
                System.out.println("client Exited...");
            }
            catch (Exception exception){
                System.err.println(exception.getMessage());
                outputStream.println("Invalid choice!");
            }

    }

    private void adminLogin() throws SocketException {
        try
        {
            if(inputStream.readLine().equals("admin") && inputStream.readLine().equals("admin"))
            {
                outputStream.println("Authenticated");
                new AdminView(outputStream,inputStream).operations();
            }
            else {
                outputStream.println("Invalid login details");
            }

        } catch (Exception exception)
        {
            System.err.println("\nCould not login: " + exception.getMessage());
            outputStream.println(exception.getMessage());
        }
    }

    private void register() {
        try {
            if(AuthenticationService.registerUser(inputStream.readLine(), inputStream.readLine(), inputStream.readLine())) {
                outputStream.println("Registered");
            } else {
                outputStream.println("Registration Failed");
            }
        } catch (Exception exception) {
            System.err.println("\nCould not Register: " + exception.getMessage());
            outputStream.println("Error: " + exception.getMessage());
        }
    }


    private void login() {
        try {

            Borrower user = AuthenticationService.signIn(inputStream.readLine(),inputStream.readLine());
            if(user != null)
            {
                outputStream.println("Authenticated");
            }
            new BorrowerView(user,outputStream,inputStream).operations();
        }catch (Exception exception){
            System.err.println("\nCould not login: " + exception.getMessage());
            outputStream.println(exception.getMessage());
        }
    }
}
