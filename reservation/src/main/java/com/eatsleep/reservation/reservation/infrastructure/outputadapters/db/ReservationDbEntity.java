package com.eatsleep.reservation.reservation.infrastructure.outputadapters.db;

import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.domain.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
public class ReservationDbEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "id_location")
    private String idLocation;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "total")
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusDbEntity status;

    @Column(name = "user")
    private String user;
    
    public Reservation toDomainModel() {
        return Reservation.builder()
            .id(UUID.fromString(this.getId()))
            .idLocation(UUID.fromString(this.getIdLocation()))
            .dateStart(this.getDateStart())
            .dateEnd(this.getDateEnd())
            .total(this.getTotal())
            .status(Status.valueOf(this.status.name()))
            .user(UUID.fromString(this.getUser()))
            .build();
    }
    
    public static ReservationDbEntity fromDomainToEntity(Reservation reservation) {
        ReservationDbEntity entity = new ReservationDbEntity();
        entity.setId(reservation.getId() != null ? reservation.getId().toString() : UUID.randomUUID().toString());
        entity.setIdLocation(reservation.getIdLocation().toString());
        entity.setDateStart(reservation.getDateStart());
        entity.setDateEnd(reservation.getDateEnd());
        entity.setTotal(reservation.getTotal());
        entity.setStatus(StatusDbEntity.valueOf(reservation.getStatus().name()));
        entity.setUser(reservation.getUser().toString());
        return entity;
    }
    
}
