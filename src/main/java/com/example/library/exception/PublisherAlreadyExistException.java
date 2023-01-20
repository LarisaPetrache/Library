package com.example.library.exception;

public class PublisherAlreadyExistException extends Exception{
    public PublisherAlreadyExistException(){
        super("This publisher already exist!");
    }
}
