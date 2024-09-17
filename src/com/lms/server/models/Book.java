package com.lms.server.models;

import java.io.Serializable;

public class Book {
    private String name;
    private String ISBN;
    private String author;
    private String genre;
    private Boolean isAvailable;

    @Override
    public String toString() {

        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ",available='" + (isAvailable?"YES":"NO") + '\'' +
                '}';
    }

    public Book( String ISBN,String name, String author, String genre) {
        this.name = name;
        this.ISBN = ISBN;
        this.author = author;
        this.genre = genre;
        this.isAvailable=true;
    }

    public Boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}

