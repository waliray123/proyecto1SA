package com.eatsleep.hotel.room.domain;

import com.eatsleep.hotel.common.DomainEntity;
import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Room {
    private UUID id;
    private int number;
    private double unitPrice;
    private int floor;
    private double maintenance;
    private String description;
    private boolean occupied;
    private Hotel hotel;
}
