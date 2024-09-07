package com.eatsleep.hotel.hotel.application.retrievehotelusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputports.RetrieveHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveHotelUseCase implements RetrieveHotelInputPort{
    
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public RetrieveHotelUseCase(HotelDbOutputAdapter hotelOutputAdapter) {
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public Optional<Hotel> getHotelById(String id) {
        // Recuperar el hotel por ID
        Optional<Hotel> hotelOptional = hotelOutputAdapter.getHotelById(id);

        // Convertir la entidad de base de datos a entidad de dominio y luego a respuesta
        return hotelOptional;
    }

    @Override
    public List<Hotel> getAllHotels() {
        // Recuperar todos los hoteles
        List<Hotel> hotels = hotelOutputAdapter.getAllHotels();
        
        // Convertir la lista de entidades a respuestas
        return hotels;
    }

}
