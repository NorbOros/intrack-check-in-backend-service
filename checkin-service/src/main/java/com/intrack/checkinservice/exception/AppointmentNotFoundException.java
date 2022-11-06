 package com.intrack.checkinservice.exception;


 public class AppointmentNotFoundException extends RuntimeException {
     private String message;

     public AppointmentNotFoundException(String message) {
         super(message);
         this.message = message;
     }
 }
