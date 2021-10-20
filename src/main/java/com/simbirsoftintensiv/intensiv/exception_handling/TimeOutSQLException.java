package com.simbirsoftintensiv.intensiv.exception_handling;

public class TimeOutSQLException extends RuntimeException {

    public TimeOutSQLException(String message) {
        super(message);
    }
}