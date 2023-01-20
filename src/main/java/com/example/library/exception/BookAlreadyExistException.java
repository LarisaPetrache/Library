package com.example.library.exception;

public class BookAlreadyExistException extends Exception{
    public BookAlreadyExistException(){
        super("This book already exists! Isbn must be unique.");
    }
}
