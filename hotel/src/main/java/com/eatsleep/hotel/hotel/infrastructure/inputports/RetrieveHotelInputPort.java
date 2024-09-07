package com.eatsleep.hotel.hotel.infrastructure.inputports;

import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response.RetrieveHotelResponse;
import java.util.List;
import java.util.Optional;

public interface RetrieveHotelInputPort {
    Optional<Hotel> getHotelById(String id);
    List<Hotel> getAllHotels();
}
