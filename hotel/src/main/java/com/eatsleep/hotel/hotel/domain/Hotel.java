package com.eatsleep.hotel.hotel.domain;

import com.eatsleep.hotel.common.DomainEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Hotel {
    
    private UUID id;
    private String name;
    private String location;    

}
