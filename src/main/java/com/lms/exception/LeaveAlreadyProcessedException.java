package com.lms.exception;

public class LeaveAlreadyProcessedException extends RuntimeException {

    public LeaveAlreadyProcessedException(String message) {
        super(message);
    }
}