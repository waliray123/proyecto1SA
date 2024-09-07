package com.eatsleep.hotel.hotel.application.existhotelusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputports.ExistHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class ExistHotelUseCase implements ExistHotelInputPort{
    
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public ExistHotelUseCase(HotelDbOutputAdapter hotelOutputAdapter) {
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public boolean validateExistHotel(String idHotel) {
        Optional<Hotel> hotelOptional = hotelOutputAdapter.getHotelById(idHotel);
        if(hotelOptional.isPresent()){
            return true;
        }
        return false;
    }

}
