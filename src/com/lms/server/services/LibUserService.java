package com.lms.server.services;

import com.lms.server.db.Books;
import com.lms.server.models.Book;

import java.util.List;
import java.util.Map;

public abstract class  LibUserService {

    public static List<Book> getAllBooks(){
        return Books.getAllBooks();
    }

    public List<Book> getBooksByName(String name){
        return Books.searchBook("name",name);
    }

    public List<Book> getBooksByAuthor(String author){
        return Books.searchBook("author",author);
    }

    public Book getBookByISBN(String ISBN) {
        return Books.getBookByISBN(ISBN);
    }

    public List<Book> getBooksByGenre(String genre){
        return Books.searchBook("genre",genre);
    }

    public Map<String,List<Book>> viewBookByGenre(){
       return Books.viewGenreWiseBooks();
    }

}
