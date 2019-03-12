package ru.alfastrah.account.sber.backend.exception;

public class SmsException extends Exception {
    public SmsException() {
        super();
    }

    public SmsException(String message) {
        super(message);
    }
}
