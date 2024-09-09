package com.eatsleep.reports.application.allearningslocationdateusecase;

import lombok.Value;

@Value
public class TopsBillDescriptionResponse {
    private String id;
    private String idBill;
    private String type;
    private String idProduct;
    private double unitPrice;
    private int quantity;
}
