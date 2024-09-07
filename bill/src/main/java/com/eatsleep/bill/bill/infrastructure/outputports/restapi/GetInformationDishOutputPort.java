package com.eatsleep.bill.bill.infrastructure.outputports.restapi;

import com.eatsleep.bill.bill.application.payrestaurantbillusecase.DishBillResponse;


public interface GetInformationDishOutputPort {
    DishBillResponse getDishInformation(String idDish);
}
