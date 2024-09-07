package com.eatsleep.bill.billdescription.infrastructure.outputadapters.db;

import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbEntity;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "bill_description")
@Getter
@Setter
@NoArgsConstructor
public class BillDescriptionDbEntity {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private BillDbEntity bill;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "id_product", nullable = false)
    private String idProduct;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Método para convertir la entidad de base de datos a un modelo de dominio
    public BillDescription toDomainModel() {
        return BillDescription.builder()
                .id(UUID.fromString(this.id))
                .bill(this.bill.toDomainModel())
                .type(this.type)
                .idProduct(UUID.fromString(this.idProduct))
                .unitPrice(this.unitPrice)
                .quantity(this.quantity)
                .build();
    }

    // Método para crear una entidad de base de datos a partir de un modelo de dominio
    public static BillDescriptionDbEntity fromDomainModel(BillDescription billDescription) {
        BillDescriptionDbEntity entity = new BillDescriptionDbEntity();
        entity.setId(billDescription.getId() != null ? billDescription.getId().toString() : UUID.randomUUID().toString());
        entity.setBill(BillDbEntity.fromDomainModel(billDescription.getBill()));
        entity.setType(billDescription.getType());
        entity.setIdProduct(billDescription.getIdProduct().toString());
        entity.setUnitPrice(billDescription.getUnitPrice());
        entity.setQuantity(billDescription.getQuantity());
        return entity;
    }
}
