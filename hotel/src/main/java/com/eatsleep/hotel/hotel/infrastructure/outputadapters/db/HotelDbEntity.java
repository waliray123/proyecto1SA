package com.eatsleep.hotel.hotel.infrastructure.outputadapters.db;

import com.eatsleep.hotel.hotel.domain.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
public class HotelDbEntity {
    
    @Id
    private String idHotel;

    @Column
    private String name;

    @Column
    private String location;

    public Hotel toDomainModel() {
        return Hotel.builder()
                .id(UUID.fromString(this.getIdHotel()))
                .name(this.getName())
                .location(this.getLocation())
                .build();
    }
    
    public static HotelDbEntity from(Hotel domain) {
        HotelDbEntity dbEntity = new HotelDbEntity();
        dbEntity.setIdHotel(domain.getId() != null ? domain.getId().toString() : UUID.randomUUID().toString());
        dbEntity.setName(domain.getName());
        dbEntity.setLocation(domain.getLocation());
        return dbEntity;
    }

}
