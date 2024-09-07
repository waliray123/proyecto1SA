package com.eatsleep.bill.bill.infrastructure.outputports.restapi;

import com.eatsleep.bill.bill.application.payhotelbillusecase.RoomBillResponse;

public interface GetInformationRoomOutputPort {
    RoomBillResponse getRoomInformation(String idRoom);
}
