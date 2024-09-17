package com.lms.server.db;

import com.lms.server.models.Borrower;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Borrowers {
    private final static Map<String, Borrower> borrowers = new ConcurrentHashMap<>();

    public static Boolean checkIfUserExist(String username){
        return borrowers.containsKey(username);
    }

    public static Borrower getBorrowerById(String username){
        return borrowers.get(username);
    }

    public static Borrower getBorrowerByusername(String username){
        return borrowers.get(username);
    }

    public static Map<String, Borrower> getUsers() {
        return borrowers;
    }

    public static void addBorrower(Borrower borrower){
        borrowers.putIfAbsent(borrower.getUsername(),borrower);
    }

    public static void addBorrower(String username, String password, String name){
        borrowers.putIfAbsent(username, new Borrower(username,password,name));
    }
}
