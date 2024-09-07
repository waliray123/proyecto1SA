package com.eatsleep.bill.billdescription.domain;

import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.common.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class BillDescription {
    private UUID id;
    private Bill bill;
    private String type;
    private UUID idProduct;
    private double unitPrice;
    private int quantity;
}
