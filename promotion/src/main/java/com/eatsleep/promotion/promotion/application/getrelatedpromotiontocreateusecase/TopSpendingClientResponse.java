package com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase;

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
