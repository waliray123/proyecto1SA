package com.eatsleep.bill.billdescription.application.paybillusecase;

import lombok.Value;

@Value
public class PayBillDescriptionRequest {
    private String idProduct;
    private double unitPrice;
    private int quantity;
}
