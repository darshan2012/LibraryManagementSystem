package com.lms.server.services;

import com.lms.server.db.Borrowers;
import com.lms.server.db.Borrowers;
import com.lms.server.exceptions.AuthenticationException;
import com.lms.server.models.Borrower;

public class AuthenticationService {

    private static Boolean checkPassword() {
        return true;
    }

    public static Borrower signIn(Borrower user){
        return signIn(user.getUsername(), user.getPassword());
    }

    public static Borrower signIn(String username, String password) {

        Borrower borrower = Borrowers.getBorrowerById(username);
        if(borrower == null)
            throw new RuntimeException("Invalid username or password");
        if (!Borrowers.getBorrowerById(username).getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }
        return borrower;
    }

    public static Boolean registerUser(Borrower user) {
        return registerUser(user.getUsername(),user.getPassword(),user.getName());
    }

    public static Boolean registerUser(String username, String password, String name){

        if(Borrowers.checkIfUserExist(username))
        {
            throw new AuthenticationException("User Already Exist");
        }
        if (!checkPassword()) {
            throw new AuthenticationException("Password regex does not match");
        }
        Borrowers.addBorrower(username,password,name);
        return true;
    }
}
