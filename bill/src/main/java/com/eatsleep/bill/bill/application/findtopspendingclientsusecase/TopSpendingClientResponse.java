package com.eatsleep.bill.bill.application.findtopspendingclientsusecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopSpendingClientResponse {
    private String userId;
    private double totalSpent;
}
