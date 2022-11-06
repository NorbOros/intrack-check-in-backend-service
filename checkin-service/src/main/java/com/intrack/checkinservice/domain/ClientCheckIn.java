package com.intrack.checkinservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCheckIn {

    private String title;
    private String appointmentId;
    private Status status;

}
