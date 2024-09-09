package com.yassirTest.fakeBank.CustomExceptions;

public class InvalidDeletionException extends RuntimeException {
    public InvalidDeletionException(String message) {
        super(message);
    }
}
