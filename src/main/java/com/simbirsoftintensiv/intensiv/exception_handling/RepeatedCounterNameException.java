package com.simbirsoftintensiv.intensiv.exception_handling;

public class RepeatedCounterNameException extends RuntimeException {

    public RepeatedCounterNameException(String message) {
        super(message);
    }
}