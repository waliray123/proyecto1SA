package com.eatsleep.hotel.hotel.application.updatehotelusecase;

import com.eatsleep.hotel.common.UseCase;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputports.UpdateHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputadapters.db.HotelDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdateHotelUseCase implements UpdateHotelInputPort{
    
    private HotelDbOutputAdapter hotelOutputAdapter;

    @Autowired
    public UpdateHotelUseCase(HotelDbOutputAdapter hotelOutputAdapter) {
        this.hotelOutputAdapter = hotelOutputAdapter;
    }

    @Override
    public Optional<Hotel> updateHotel(String idHotel, UpdateHotelRequest hotelRequest) {
         // Validar la información del hotel
        if (hotelRequest.getName() == null || hotelRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del hotel no puede estar vacío");
        }
        if (hotelRequest.getLocation() == null || hotelRequest.getLocation().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del hotel no puede estar vacía");
        }

        // Buscar el hotel existente en la base de datos
        Optional<Hotel> existingHotelOptional = hotelOutputAdapter.getHotelById(idHotel);
        if (existingHotelOptional.isEmpty()) {
            return Optional.empty();  // Hotel no encontrado, retorno vacío
        }

        // Actualizar los campos del hotel existente
        Hotel existingHotel = existingHotelOptional.get();
        existingHotel.setName(hotelRequest.getName());
        existingHotel.setLocation(hotelRequest.getLocation());

        // Guardar el hotel actualizado
        Optional<Hotel> updatedHotelOptional = hotelOutputAdapter.updateHotel(idHotel, existingHotel);
        
        // Crear y devolver la respuesta
        return updatedHotelOptional;
    }



}
