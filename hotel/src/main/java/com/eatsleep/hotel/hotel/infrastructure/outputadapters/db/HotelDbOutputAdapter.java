package com.eatsleep.hotel.hotel.infrastructure.outputadapters.db;

import com.eatsleep.hotel.common.OutputAdapter;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.outputports.db.CreateHotelOutputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputports.db.RetrieveHotelOutputPort;
import com.eatsleep.hotel.hotel.infrastructure.outputports.db.UpdateHotelOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class HotelDbOutputAdapter implements CreateHotelOutputPort,UpdateHotelOutputPort,RetrieveHotelOutputPort{
    
    private HotelDbEntityRepository hotelDbEntityRepository;

    @Autowired
    public HotelDbOutputAdapter(HotelDbEntityRepository hotelDbEntityRepository) {
        this.hotelDbEntityRepository = hotelDbEntityRepository;
    }
    
    @Override
    public Hotel createHotel(Hotel hotel) {
        HotelDbEntity hotelEntity = HotelDbEntity.from(hotel);
        hotelEntity = hotelDbEntityRepository.save(hotelEntity);
        return hotelEntity.toDomainModel();
    }

    @Override
    public Optional<Hotel> updateHotel(String id, Hotel updatedHotel) {
        return hotelDbEntityRepository.findById(id)
                .map(existingHotelEntity -> {                    
                    existingHotelEntity.setName(updatedHotel.getName());
                    existingHotelEntity.setLocation(updatedHotel.getLocation());
                    
                    HotelDbEntity savedHotelEntity = hotelDbEntityRepository.save(existingHotelEntity);

                    return savedHotelEntity.toDomainModel();
                });
    }

    @Override
    public Optional<Hotel> getHotelById(String id) {
        Optional<HotelDbEntity> hotelEntity = hotelDbEntityRepository.findById(id);
        return hotelEntity
                .map(hotelDbEntity -> hotelDbEntity.toDomainModel());
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelDbEntityRepository.findAll().stream()
                .map(hotelDbEntity -> hotelDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

}
