package com.eatsleep.restaurant.dish.domain;

import com.eatsleep.restaurant.common.DomainEntity;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Dish {
    private UUID id;
    private String name;
    private double price;
    private String description;
    private Restaurant restaurant;
}
