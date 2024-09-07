package com.eatsleep.hotel.hotel.infrastructure.inputports;

import com.eatsleep.hotel.hotel.application.updatehotelusecase.UpdateHotelRequest;
import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.Optional;

public interface UpdateHotelInputPort {
    Optional<Hotel> updateHotel(String idHotel, UpdateHotelRequest hotel);
}
