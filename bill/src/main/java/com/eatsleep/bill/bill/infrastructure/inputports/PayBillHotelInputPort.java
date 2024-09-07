package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.application.payhotelbillusecase.PayBillHotelRequest;
import com.eatsleep.bill.bill.domain.Bill;


public interface PayBillHotelInputPort {
    Bill makeBill(PayBillHotelRequest bill, String idUser, String idHotel) throws BillException;
}
