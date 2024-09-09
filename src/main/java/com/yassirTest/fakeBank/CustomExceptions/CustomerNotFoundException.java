package com.yassirTest.fakeBank.CustomExceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException (String message){
        super(message);
    }
        }