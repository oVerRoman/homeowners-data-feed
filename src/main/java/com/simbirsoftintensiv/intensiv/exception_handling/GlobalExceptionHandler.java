package com.simbirsoftintensiv.intensiv.exception_handling;

import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//todo обязательно добавить логи тут

@ControllerAdvice
public class GlobalExceptionHandler {

    static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NotFoundException exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(IncorrectCounterValueException exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(RepeatedCounterNameException exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(IncorectDataDuringRegistration exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("Incorrect data during registration!", exception);
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(TimeOutSQLException exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(DataAccessResourceFailureException exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(Exception exception) {
        IncorrectData data = new IncorrectData();
        data.setInfo(exception.getMessage());
        log.warn("An exception! Oops!", exception);
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}