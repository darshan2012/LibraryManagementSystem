package com.lms.server.services;

import com.lms.server.db.Books;
import com.lms.server.exceptions.BookNotFoundException;
import com.lms.server.exceptions.BookUnavailableException;
import com.lms.server.models.Admin;
import com.lms.server.models.Book;
import com.lms.server.models.User;

public class AdminService extends LibUserService {
    public static void addBook(Book book) {
        if (!Books.checkIfISBNExist(book.getISBN())) {
            Books.addBook(book.getISBN(), book.getName(), book.getAuthor(), book.getGenre());
        } else {
            throw new RuntimeException("Book Already Exist!");
        }
    }

    public static void removeBook(String ISBN) {
        if (Books.checkIfISBNExist(ISBN)) {
            if (!Books.getBookByISBN(ISBN).getIsAvailable()) {
                throw new BookUnavailableException();
            }
            Books.removeBook(ISBN);
        }  else {
            throw new BookNotFoundException();
        }
    }



}
