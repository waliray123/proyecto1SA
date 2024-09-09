package com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db;

import com.eatsleep.reservation.reservation.infrastructure.outputadapters.db.ReservationDbEntity;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "reservation_description")
@Getter
@Setter
@NoArgsConstructor
public class ReservationDescriptionDbEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "id_product")
    private String idProduct;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationDbEntity reservation;
    
    public ReservationDescription toDomainModel() {
        return ReservationDescription.builder()
            .id(UUID.fromString(this.getId()))
            .idProduct(UUID.fromString(this.getIdProduct()))
            .unitPrice(this.getUnitPrice())
            .quantity(this.getQuantity())
            .reservation(this.getReservation() != null ? this.getReservation().toDomainModel() : null)
            .build();
    }
    
    public static ReservationDescriptionDbEntity fromDomainToEntity(ReservationDescription reservationDescription) {
        ReservationDescriptionDbEntity entity = new ReservationDescriptionDbEntity();
        entity.setId(reservationDescription.getId() != null ? reservationDescription.getId().toString() : UUID.randomUUID().toString());
        entity.setIdProduct(reservationDescription.getIdProduct().toString());
        entity.setUnitPrice(reservationDescription.getUnitPrice());
        entity.setQuantity(reservationDescription.getQuantity());
        entity.setReservation(reservationDescription.getReservation() != null ? ReservationDbEntity.fromDomainToEntity(reservationDescription.getReservation()) : null);  // Conversión de la reserva
        return entity;
    }
    
}
