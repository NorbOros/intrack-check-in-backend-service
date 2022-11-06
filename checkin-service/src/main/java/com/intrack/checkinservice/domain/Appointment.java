package com.intrack.checkinservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class Appointment {

    @Id
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String registrationNm;
    private LocalDateTime dueDateTime;
    private LocalDateTime checkInDateTime;
    private LocalDateTime lastStatusChange;
    private Status status;
}
