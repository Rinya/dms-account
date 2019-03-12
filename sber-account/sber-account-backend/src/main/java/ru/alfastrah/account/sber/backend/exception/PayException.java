package ru.alfastrah.account.sber.backend.exception;

public class PayException extends Exception {
    public PayException() {
    }

    public PayException(String message) {
        super(message);
    }
}
