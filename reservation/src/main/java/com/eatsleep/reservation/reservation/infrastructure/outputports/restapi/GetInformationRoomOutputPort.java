package com.eatsleep.reservation.reservation.infrastructure.outputports.restapi;

import com.eatsleep.reservation.reservation.application.makereservation.RoomReservationResponse;

public interface GetInformationRoomOutputPort {
    RoomReservationResponse getRoomInformation(String idRoom);
}
