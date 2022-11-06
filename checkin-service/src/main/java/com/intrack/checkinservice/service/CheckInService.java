package com.intrack.checkinservice.service;

import com.intrack.checkinservice.domain.Appointment;
import com.intrack.checkinservice.domain.ClientCheckIn;
import com.intrack.checkinservice.domain.Status;
import com.intrack.checkinservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class CheckInService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private Sinks.Many<Appointment> appointmentSink = Sinks.many().replay().latest();

    public Mono<Appointment> getAppointmentById(final String id) {
        return appointmentRepository.findById(id);
    }

    public Mono<Appointment> checkInToAppointment(final ClientCheckIn clientCheckIn) {
        return clientCheckIn.getAppointmentId() == null ? checkInToNewAppointment(clientCheckIn) : checkInToExistingAppointment(clientCheckIn);
    }

    private Mono<Appointment> checkInToNewAppointment(final ClientCheckIn clientCheckIn) {
        Appointment appointment = Appointment.builder()
                                             .registrationNm(getRegistrationNumber())
                                             .title(clientCheckIn.getTitle())
                                             .checkInDateTime(LocalDateTime.now())
                                             .lastStatusChange(LocalDateTime.now())
                                             .dueDateTime(LocalDateTime.now())
                                             .status(Status.CREATED)
                                             .build();

        return appointmentRepository.save(appointment);
    }

    private String getRegistrationNumber() {
        final int randomNumber = new Random().ints(1, 100)
                                             .findFirst()
                                             .getAsInt();
        final UUID uuid = UUID.randomUUID();

        return uuid.toString().substring(1, 4) + randomNumber;
    }

    private Mono<Appointment> checkInToExistingAppointment(final ClientCheckIn clientCheckIn) {

        return fetchAppByRegId(clientCheckIn)
                .flatMap(this::checkInAndPersistAppointment)
                .doOnNext(appointmentSink::tryEmitNext);
    }

    public Mono<Appointment> changeAppointmentStatus(final ClientCheckIn clientCheckIn) {
        return fetchAppByRegId(clientCheckIn)
                .flatMap(appointment -> changeAppointmentStatus(appointment, clientCheckIn.getStatus()));
    }

    public Flux<Appointment> streamCheckedInAppointments() {
        return appointmentSink.asFlux();
    }


    /**
     * TODO -- it should be migrated to the Appointment registration service
     *
     * @return
     */
    public Mono<Appointment> registerAppointment() {
        int random = new Random().ints(1, 100)
                                 .findFirst()
                                 .getAsInt();

        Appointment appointment = Appointment.builder()
                                             .title("Appointment - " + random)
                                             .dueDateTime(LocalDateTime.now().plusDays(7))
                                             .firstName("John - " + random)
                                             .lastName("Doe - " + random)
                                             .lastStatusChange(LocalDateTime.now())
                                             .status(Status.CREATED)
                                             .build();

        return appointmentRepository.save(appointment);

    }

    private Mono<Appointment> checkInAndPersistAppointment(final Appointment appointment) {
        appointment.setStatus(Status.CHECKED_IN);
        appointment.setLastStatusChange(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    private Mono<Appointment> changeAppointmentStatus(final Appointment appointment, final Status status) {
        appointment.setLastStatusChange(LocalDateTime.now());
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    private Mono<Appointment> fetchAppByRegId(final ClientCheckIn clientCheckIn) {
        return appointmentRepository.findById(clientCheckIn.getAppointmentId());
    }


}
