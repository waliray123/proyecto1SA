package com.eatsleep.restaurant.dish.infrastructure.outputadapters.db;

import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.db.RestaurantDbEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class DishDbEntity {

    @Id
    private String idDish;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description", length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "restaurant_id_restaurant", nullable = false)
    private RestaurantDbEntity restaurant;

    public Dish toDomainModel() {
        return Dish.builder()
                .id(UUID.fromString(this.getIdDish()))
                .name(this.getName())
                .price(this.getPrice())
                .description(this.getDescription())
                .restaurant(this.getRestaurant().toDomainModel())
                .build();
    }

    public static DishDbEntity fromDomainModel(Dish dish) {
        DishDbEntity dbEntity = new DishDbEntity();
        dbEntity.setIdDish(dish.getId().toString());
        dbEntity.setName(dish.getName());
        dbEntity.setPrice(dish.getPrice());
        dbEntity.setDescription(dish.getDescription());
        dbEntity.setRestaurant(RestaurantDbEntity.from(dish.getRestaurant()));
        return dbEntity;
    }
}
