package com.eatsleep.hotel.hotel.infrastructure.outputports.db;

import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.List;
import java.util.Optional;

public interface RetrieveHotelOutputPort {
    Optional<Hotel> getHotelById(String id);
    List<Hotel> getAllHotels();
}
