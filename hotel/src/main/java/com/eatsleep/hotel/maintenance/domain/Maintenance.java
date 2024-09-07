package com.eatsleep.hotel.maintenance.domain;

import com.eatsleep.hotel.common.DomainEntity;
import com.eatsleep.hotel.room.domain.Room;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Maintenance {
    private UUID idMaintenance;
    private LocalDate dateMaintenance;
    private Double maintenance;
    private Room room;
}
