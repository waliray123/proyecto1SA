package com.eatsleep.reservation.reservation.domain;

import com.eatsleep.reservation.common.DomainEntity;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Reservation {
    private UUID id;
    private UUID idLocation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double total;
    private Status status;
    private UUID user;
    private List<ReservationDescription> descriptions;
}
