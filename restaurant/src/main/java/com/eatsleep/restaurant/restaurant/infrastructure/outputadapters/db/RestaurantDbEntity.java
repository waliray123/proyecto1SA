package com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db;

import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantDbEntity {

    @Id
    private String idRestaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "id_hotel")
    private String idHotel;
    
    public Restaurant toDomainModel() {
        return Restaurant.builder()
                .id(UUID.fromString(this.getIdRestaurant()))
                .name(this.getName())
                .location(this.getLocation())
                .idHotel(this.getIdHotel() != null ? UUID.fromString(this.getIdHotel()) : null)
                .build();
    }
    
    public static RestaurantDbEntity from(Restaurant restaurant) {
        RestaurantDbEntity dbEntity = new RestaurantDbEntity();
        dbEntity.setIdRestaurant(restaurant.getId() != null ? restaurant.getId().toString() : UUID.randomUUID().toString());
        dbEntity.setName(restaurant.getName());
        dbEntity.setLocation(restaurant.getLocation());
        dbEntity.setIdHotel(restaurant.getIdHotel() != null ? restaurant.getIdHotel().toString() : null);
        
        return dbEntity;
    }
}
