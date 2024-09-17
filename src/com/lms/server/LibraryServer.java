package com.lms.server;

import java.io.*;
import java.net.*;

import com.lms.server.serverView.LMS;

public class LibraryServer {
    private ServerSocket serverSocket;

    public LibraryServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Server started on port " + serverSocket.getLocalPort());
        while (true) {
            new ClientHandler(serverSocket.accept()).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader inStream;
        private PrintWriter outStream;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        public void run() {
            try {
                System.out.println("Client connected " + Thread.currentThread().getName());

                outStream.println("Welcome to the Library Management System!");
                new LMS(outStream, inStream).start();

            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Client disconnected " + Thread.currentThread().getName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        try {
            LibraryServer server = new LibraryServer(6666);
            server.start();
        } catch (IOException e) {
            System.out.println("Could not start Server");
        }
    }
}
