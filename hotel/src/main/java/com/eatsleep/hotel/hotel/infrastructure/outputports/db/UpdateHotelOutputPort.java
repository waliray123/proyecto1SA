package com.eatsleep.hotel.hotel.infrastructure.outputports.db;

import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.Optional;

public interface UpdateHotelOutputPort {
    Optional<Hotel> updateHotel(String id, Hotel updatedHotel);
}
