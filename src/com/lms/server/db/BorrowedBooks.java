package com.lms.server.db;

import com.lms.server.models.BorrowedBook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BorrowedBooks {
    private final static Map<String, BorrowedBook> borrowedBooks = new ConcurrentHashMap<>();
    public static boolean checkIfISBNExist(String ISBN){
        return borrowedBooks.containsKey(ISBN);
    }
    public static void removeBook(String ISBN){
        borrowedBooks.remove(ISBN);
    }
    public static void addBorrowedBook(BorrowedBook borrowedBook){
        borrowedBooks.putIfAbsent(borrowedBook.getBook().getISBN(),borrowedBook);
    }
    public static void removeBorrowedBook(String ISBN) {
        borrowedBooks.remove(ISBN);
    }
    public static BorrowedBook getBorrowedBook(String ISBN) {
        return borrowedBooks.get(ISBN);
    }
}

