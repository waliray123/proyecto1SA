package com.eatsleep.restaurant.restaurant.domain;

import com.eatsleep.restaurant.common.DomainEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Restaurant {
    
    private UUID id;
    private String name;
    private String location;
    private UUID idHotel;

}
