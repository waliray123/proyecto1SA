package com.eatsleep.bill.bill.infrastructure.outputports.restapi;

import com.eatsleep.bill.bill.application.payhotelbillusecase.GetReservationAllDescriptionResponse;

public interface GetInformationReservationOutputPort {
    GetReservationAllDescriptionResponse getReservationInformation(String idReservation);
}
