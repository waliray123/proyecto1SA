package com.eatsleep.bill.bill.infrastructure.outputadapters.db;

import com.eatsleep.bill.bill.domain.Bill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
public class BillDbEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "id_location")
    private String idLocation;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reservation_id")
    private String reservationId;

    // Método para convertir la entidad de base de datos a un modelo de dominio
    public Bill toDomainModel() {
        return Bill.builder()
                .id(UUID.fromString(this.getId()))
                .type(this.getType())
                .idLocation(UUID.fromString(this.getIdLocation()))
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .total(this.getTotal())
                .userId(UUID.fromString(this.getUserId()))
                .reservationId(UUID.fromString(this.getReservationId()))
                .build();
    }

    // Método para crear una entidad de base de datos a partir de un modelo de dominio
    public static BillDbEntity fromDomainModel(Bill bill) {
        BillDbEntity billDbEntity = new BillDbEntity();
        billDbEntity.setId(bill.getId().toString());
        billDbEntity.setType(bill.getType());
        billDbEntity.setIdLocation(bill.getIdLocation().toString());
        billDbEntity.setStartDate(bill.getStartDate());
        billDbEntity.setEndDate(bill.getEndDate());
        billDbEntity.setTotal(bill.getTotal());
        billDbEntity.setUserId(bill.getUserId().toString());
        billDbEntity.setReservationId(bill.getReservationId().toString());
        return billDbEntity;
    }
}
