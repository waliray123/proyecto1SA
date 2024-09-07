package com.eatsleep.reservation.reservationdescription.domain;

import com.eatsleep.reservation.common.DomainEntity;
import com.eatsleep.reservation.reservation.domain.Reservation;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class ReservationDescription {
    private UUID id;
    private UUID idProduct;
    private double unitPrice;
    private int quantity;
    private Reservation reservation;
}
