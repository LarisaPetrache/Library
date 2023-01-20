package com.example.library.exception;

public class MemberEmailAlreadyExistException extends Exception{
    public MemberEmailAlreadyExistException(){
        super("This email already exist!");
    }
}
