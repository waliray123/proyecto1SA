package com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response;

import com.eatsleep.promotion.promotion.domain.Promotion;
import java.time.LocalDate;
import lombok.Value;

@Value
public class RetrievePromotionResponse {

    private String idPromotion;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String description;
    private String typeClient;
    private String idProduct;
    private double valuePromotion;   

    public RetrievePromotionResponse(Promotion promotion) {
        this.idPromotion = promotion.getIdPromotion().toString();
        this.startDate = promotion.getStartDate();
        this.endDate = promotion.getEndDate();
        this.type = promotion.getType();
        this.description = promotion.getDescription();
        this.typeClient = promotion.getTypeClient();
        this.idProduct = promotion.getIdProduct().toString();
        this.valuePromotion = promotion.getValuePromotion();
    }

    
    

    
}
