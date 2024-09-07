package com.eatsleep.bill.bill.infrastructure.outputports.db;

import com.eatsleep.bill.bill.domain.Bill;

public interface PayRestaurantBillOutputPort {
    Bill payRestaurantBill(Bill bill);
}
