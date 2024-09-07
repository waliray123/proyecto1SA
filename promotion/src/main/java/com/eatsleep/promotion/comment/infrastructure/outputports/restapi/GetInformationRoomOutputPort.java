package com.eatsleep.promotion.comment.infrastructure.outputports.restapi;

import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.RoomPromotionResponse;

public interface GetInformationRoomOutputPort {
    RoomPromotionResponse getRoomInformation(String idRoom);
}
