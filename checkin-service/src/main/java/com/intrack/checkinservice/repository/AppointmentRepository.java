 package com.intrack.checkinservice.repository;

 import com.intrack.checkinservice.domain.Appointment;
 import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

 public interface AppointmentRepository extends ReactiveMongoRepository<Appointment, String> {
 }
