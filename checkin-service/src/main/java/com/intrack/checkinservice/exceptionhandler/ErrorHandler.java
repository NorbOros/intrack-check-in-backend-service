 package com.intrack.checkinservice.exceptionhandler;

 import com.intrack.checkinservice.exception.AppointmentNotFoundException;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.bind.annotation.ResponseStatus;

 @ControllerAdvice
 public class ErrorHandler {

     @ResponseBody
     @ResponseStatus(HttpStatus.NOT_FOUND)
     @ExceptionHandler(AppointmentNotFoundException.class)
     public ResponseEntity<String> handleMovieInfoNotFoundException(AppointmentNotFoundException ex) {
         ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
     }
 }
