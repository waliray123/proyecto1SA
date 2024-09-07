package com.eatsleep.promotion.promotion.infrastructure.outputadapters.db;

import com.eatsleep.promotion.promotion.domain.Promotion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "promotion")
public class PromotionDbEntity {

    @Id
    @Column(name = "id_promotion", columnDefinition = "CHAR(36)")
    private String idPromotion;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "type", length = 45)
    private String type;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "type_client", length = 45)
    private String typeClient;

    @Column(name = "id_product", columnDefinition = "CHAR(36)")
    private String idProduct;
    
    @Column(name = "value_promotion")
    private double valuePromotion;

    public Promotion toDomainModel() {
        return Promotion.builder()
            .idPromotion(UUID.fromString(this.getIdPromotion()))
            .startDate(this.getStartDate())
            .endDate(this.getEndDate())
            .type(this.getType())
            .description(this.getDescription())
            .typeClient(this.getTypeClient())
            .idProduct(UUID.fromString(this.getIdProduct()))
            .valuePromotion(this.getValuePromotion())
            .build();
    }

    public static PromotionDbEntity from(Promotion promotion) {
        PromotionDbEntity promotionDbEntity = new PromotionDbEntity();
        promotionDbEntity.setIdPromotion(promotion.getIdPromotion() != null ? promotion.getIdPromotion().toString() : UUID.randomUUID().toString());
        promotionDbEntity.setStartDate(promotion.getStartDate());
        promotionDbEntity.setEndDate(promotion.getEndDate());
        promotionDbEntity.setType(promotion.getType());
        promotionDbEntity.setDescription(promotion.getDescription());
        promotionDbEntity.setTypeClient(promotion.getTypeClient());
        promotionDbEntity.setIdProduct(promotion.getIdProduct().toString());
        promotionDbEntity.setValuePromotion(promotion.getValuePromotion());
        return promotionDbEntity;
    }
}
