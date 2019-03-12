package ru.alfastrah.account.sber.backend.exception;

public class AuthException extends Exception {
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
