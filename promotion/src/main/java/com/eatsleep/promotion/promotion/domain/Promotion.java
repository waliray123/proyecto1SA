package com.eatsleep.promotion.promotion.domain;

import com.eatsleep.promotion.common.DomainEntity;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@DomainEntity
public class Promotion {
    private UUID idPromotion;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String description;
    private String typeClient;
    private UUID idProduct;
    private double valuePromotion;
}
