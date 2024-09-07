package com.eatsleep.hotel.hotel.application.createhotelusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputports.CreateHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateHotelUseCase implements CreateHotelInputPort{
    
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public CreateHotelUseCase(HotelDbOutputAdapter hotelOutputAdapter) {
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public Hotel createHotel(CreateHotelRequest hotelRequest) {        
        // Validar la información del hotel
        if (hotelRequest.getName() == null || hotelRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del hotel no puede estar vacío");
        }
        if (hotelRequest.getLocation() == null || hotelRequest.getLocation().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del hotel no puede estar vacía");
        }
        
        // Convertir la entidad de request en una entidad de base de datos
        Hotel hotel = Hotel.builder()                
                .name(hotelRequest.getName())
                .location(hotelRequest.getLocation())
                .build();
        
        // Persistir el hotel en la base de datos usando el Output Adapter
        Hotel savedHotel = hotelOutputAdapter.createHotel(hotel);
        
        // Crear el hotel
        
        
        return savedHotel;
    }

}
