package com.lms.server.serverView;

import com.lms.server.models.Borrower;
import com.lms.server.services.AuthenticationService;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.SocketException;

public class LMS {
    private static LMS lms;
    private PrintWriter outStream;
    private BufferedReader inStream;

    public LMS(PrintWriter outStream, BufferedReader inStream) {
        this.outStream = outStream;
        this.inStream = inStream;

    }
    public void start(){

        int choice=0;

            try{
                do{
                choice = Integer.parseInt(inStream.readLine());

                switch (choice){
                    case 1 -> login();
                    case 2 -> register();
                    case 555-> adminLogin();
                    case 3 -> System.out.println("Exiting...");
                    default -> System.err.println("Invalid choice!");
                }
                }while(choice != 3);
            }
            catch (SocketException e)
            {
                System.out.println("client Exited...");
            }
            catch (Exception e){
                System.err.println(e.getMessage());
                outStream.println("Invalid choice!");
            }

    }

    private void adminLogin() throws SocketException {
        new AdminView(outStream,inStream).operations();
    }

    private void register() {
        try {
            if(AuthenticationService.registerUser(inStream.readLine(), inStream.readLine(), inStream.readLine())) {
                outStream.println("Registered");
            } else {
                outStream.println("Registration Failed");
            }
        } catch (Exception e) {
            System.err.println("\nCould not Register: " + e.getMessage());
            outStream.println("Error: " + e.getMessage());
        }
    }


    private void login() {
        try {

            Borrower user = AuthenticationService.signIn(inStream.readLine(),inStream.readLine());
            if(user != null)
            {
                outStream.println("Authenticated");
            }
            new BorrowerView(user,outStream,inStream).operations();
        }catch (Exception e){
            System.err.println("\nCould not login: " + e.getMessage());
            outStream.println(e.getMessage());
        }
    }
}
