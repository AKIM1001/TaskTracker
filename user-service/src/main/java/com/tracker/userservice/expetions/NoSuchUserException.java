package com.tracker.userservice.expetions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
        super("No such lecture");
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}

