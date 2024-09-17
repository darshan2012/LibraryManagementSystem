package com.lms.server.services;

import com.lms.server.db.BooksDB;
import com.lms.server.exceptions.BookNotFoundException;
import com.lms.server.exceptions.BookUnavailableException;
import com.lms.server.models.Admin;
import com.lms.server.models.Book;
import com.lms.server.models.User;

public class AdminService extends LibUserService {
    public static void addBook(Book book) {
        if (!BooksDB.checkIfISBNExist(book.getISBN())) {
            BooksDB.addBook(book.getISBN(), book.getName(), book.getAuthor(), book.getGenre());
        } else {
            throw new RuntimeException("Book Already Exist!");
        }
    }

    public static void removeBook(String ISBN) {
        if (BooksDB.checkIfISBNExist(ISBN)) {
            if (!BooksDB.getBookByISBN(ISBN).getIsAvailable()) {
                throw new BookUnavailableException();
            }
            BooksDB.removeBook(ISBN);
        }  else {
            throw new BookNotFoundException();
        }
    }



}
