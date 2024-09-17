package com.lms.server.services;

import com.lms.server.db.Books;
import com.lms.server.db.BorrowedBooks;
import com.lms.server.db.Borrowers;
import com.lms.server.exceptions.BookNotFoundException;
import com.lms.server.exceptions.BookUnavailableException;
import com.lms.server.models.Book;
import com.lms.server.models.BorrowedBook;
import com.lms.server.models.Borrower;
import com.lms.server.util.PenaltyCalculator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class BorrowerService extends LibUserService {
    public static int MAX_BORROW_LIMIT = 5;
    public static int MAX_HOLD_DAYS = 14;
    public static long PENALTY_PER_DAY = 10;
    public static PenaltyCalculator penaltyCalculator;

    static {
        penaltyCalculator = (issueDate, dueDate) -> {
            long penalty = (Duration.between(issueDate,dueDate).toDays() - MAX_HOLD_DAYS) * PENALTY_PER_DAY;
            return penalty > 0 ? penalty : 0;
        };
    }


    private Borrower borrower;

    public BorrowerService(Borrower borrower) {
        this.borrower = borrower;
    }

    public  boolean issueBook(String ISBN){
        if(!Books.checkIfISBNExist(ISBN))
        {
            throw new BookNotFoundException();
        }
        Book book = Books.getBookByISBN(ISBN);
        if (!book.getIsAvailable()) {
            throw new BookUnavailableException();
        }
        book.setIsAvailable(false);
        Borrower borrower = Borrowers.getBorrowerByusername(this.borrower.getUsername());
        BorrowedBook borrowedBook = new BorrowedBook(book);
        BorrowedBooks.addBorrowedBook(borrowedBook);
        borrower.addBookToBorrowedBook(borrowedBook);
        return true;
    }

    public long returnBook(String ISBN){
        if(!Books.checkIfISBNExist(ISBN))
        {
            throw new BookNotFoundException();
        }
        Book book = Books.getBookByISBN(ISBN);
        if (book.getIsAvailable()) {
            throw new BookUnavailableException("Book is Already Available");
        }
        book.setIsAvailable(true);
        BorrowedBook borrowedBook = BorrowedBooks.getBorrowedBook(ISBN);
        long penalty = penaltyCalculator.calculatePenalty(borrowedBook.getIssueDate(), LocalDateTime.now());
        borrower.removeBookFromBorrow(ISBN);
        BorrowedBooks.removeBorrowedBook(ISBN);
        return penalty;
    }

    public List<BorrowedBook> getBorrowedBooks(){
        return borrower.getBorrowedBooks();
    }
}
