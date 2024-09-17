package com.lms.server.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(){super("Could not login");}
    public AuthenticationException(String message) {
        super(message);
    }
}
