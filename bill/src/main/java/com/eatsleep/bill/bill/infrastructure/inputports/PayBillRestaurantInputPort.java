package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.application.payrestaurantbillusecase.PayBillRestaurantRequest;
import com.eatsleep.bill.bill.domain.Bill;


public interface PayBillRestaurantInputPort {
    Bill makeBill(PayBillRestaurantRequest bill, String idUser, String idRestaurant) throws BillException;
}
