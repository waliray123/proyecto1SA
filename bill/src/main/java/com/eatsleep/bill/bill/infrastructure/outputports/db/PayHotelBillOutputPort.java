package com.eatsleep.bill.bill.infrastructure.outputports.db;

import com.eatsleep.bill.bill.domain.Bill;

public interface PayHotelBillOutputPort {
    Bill payHotelBill(Bill bill);
}
