package com.example.library.exception;

public class GenreAlreadyExistException extends Exception{
    public GenreAlreadyExistException(){
        super("This genre already exist!");
    }
}
