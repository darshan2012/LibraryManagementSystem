package com.lms.server.services;

import com.lms.server.db.BooksDB;
import com.lms.server.models.Book;

import java.util.List;
import java.util.Map;

public abstract class  LibUserService {

    public static List<Book> getAllBooks(){
        return BooksDB.getAllBooks();
    }

    public List<Book> searchBooksByName(String name){
        return BooksDB.searchBook("name",name);
    }

    public List<Book> searchBooksByAuthor(String author){
        return BooksDB.searchBook("author",author);
    }

    public Book getBookByISBN(String ISBN) {
        return BooksDB.getBookByISBN(ISBN);
    }

    public List<Book> getBooksByGenre(String genre){
        return BooksDB.searchBook("genre",genre);
    }

    public Map<String,List<Book>> viewBookByGenre(){
       return BooksDB.viewGenreWiseBooks();
    }

}
