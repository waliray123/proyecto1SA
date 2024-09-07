package com.eatsleep.hotel.hotel.infrastructure.inputports;

import com.eatsleep.hotel.hotel.application.createhotelusecase.CreateHotelRequest;
import com.eatsleep.hotel.hotel.domain.Hotel;

public interface CreateHotelInputPort {
    
    Hotel createHotel(CreateHotelRequest hotel);

}
