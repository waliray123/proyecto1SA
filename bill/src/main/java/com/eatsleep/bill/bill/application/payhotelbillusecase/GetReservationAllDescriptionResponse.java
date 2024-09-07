package com.eatsleep.bill.bill.application.payhotelbillusecase;

import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class GetReservationAllDescriptionResponse {
    private String id;
    private String idLocation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double total;
    private Status status;
    private String user;
    private List<GetReservationDescriptionResponse> descriptions;
}