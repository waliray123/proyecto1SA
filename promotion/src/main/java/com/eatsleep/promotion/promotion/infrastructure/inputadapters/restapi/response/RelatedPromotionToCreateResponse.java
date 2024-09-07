package com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response;

import lombok.Value;

@Value
public class RelatedPromotionToCreateResponse {
    private String type;
    private String description;
    private String typeClient;
    private String idProduct;
    private String nameProduct;
    private String value;

    public RelatedPromotionToCreateResponse(String type, String description, String typeClient, String idProduct, String nameProduct, String value) {
        this.type = type;
        this.description = description;
        this.typeClient = typeClient;
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.value = value;
    }
    
    
}
