package com.simbirsoftintensiv.intensiv.exception_handling;

import com.simbirsoftintensiv.intensiv.util.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Message> handleMaxUploadSizeExceedException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body((new Message("Upload file too large.")));
    }
}
