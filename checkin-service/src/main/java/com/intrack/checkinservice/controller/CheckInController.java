 package com.intrack.checkinservice.controller;

 import com.intrack.checkinservice.exception.AppointmentNotFoundException;
 import com.intrack.checkinservice.domain.Appointment;
 import com.intrack.checkinservice.domain.ClientCheckIn;
 import com.intrack.checkinservice.service.CheckInService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.http.codec.ServerSentEvent;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import reactor.core.publisher.Flux;
 import reactor.core.publisher.Mono;

 @RestController
 @RequestMapping("/v1/check-in")
 public class CheckInController {

     @Autowired
     private CheckInService checkInService;

     @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public Mono<ResponseEntity<Appointment>> getAppointmentById(@PathVariable("id") final String id) {
         return checkInService.getAppointmentById(id)
                              .map(ResponseEntity.ok()::body)
                              .switchIfEmpty(Mono.error(new AppointmentNotFoundException("Appointment with ID: " + id + " not found.")));
     }

     @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public Mono<ResponseEntity<Appointment>> checkInForAppointment(@RequestBody final ClientCheckIn clientCheckIn) {
         return checkInService.checkInToAppointment(clientCheckIn)
                              .map(ResponseEntity.ok()::body);

     }

     @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
     public Flux<ServerSentEvent<Appointment>> stream() {
         return checkInService.streamCheckedInAppointments()
                              .map(appointment -> ServerSentEvent.<Appointment>builder()
                                                                 .id(appointment.getId())
                                                                 .event(appointment.getStatus().name())
                                                                 .data(appointment)
                                                                 .build());
     }

     @PostMapping(value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
     public Mono<ResponseEntity<Appointment>> changeAppointmentStatus(@RequestBody final ClientCheckIn clientCheckIn) {
         return checkInService.changeAppointmentStatus(clientCheckIn)
                              .map(ResponseEntity.ok()::body);

     }

     @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
     public Mono<ResponseEntity<Appointment>> registerAppointment() {
         return checkInService.registerAppointment()
                              .map(ResponseEntity.ok()::body);
     }

 }
