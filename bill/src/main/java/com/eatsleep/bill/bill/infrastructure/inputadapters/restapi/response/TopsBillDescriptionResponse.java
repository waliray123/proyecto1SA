package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import lombok.Value;

@Value
public class TopsBillDescriptionResponse {
    private String id;
    private String idBill;
    private String type;
    private String idProduct;
    private double unitPrice;
    private int quantity;
    
    public TopsBillDescriptionResponse(BillDescription billDescription) {
        this.id = billDescription.getId().toString();
        this.idBill = billDescription.getBill().getId().toString();
        this.type = billDescription.getType();
        this.idProduct = billDescription.getIdProduct().toString();
        this.unitPrice = billDescription.getUnitPrice();
        this.quantity = billDescription.getQuantity();
    }
}
