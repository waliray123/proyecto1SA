package com.eatsleep.hotel.hotel.infrastructure.outputports.db;

import com.eatsleep.hotel.hotel.domain.Hotel;

public interface CreateHotelOutputPort {
    Hotel createHotel(Hotel hotel);
}
