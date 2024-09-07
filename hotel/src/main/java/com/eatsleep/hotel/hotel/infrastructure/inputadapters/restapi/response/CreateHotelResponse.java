package com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.hotel.domain.Hotel;
import lombok.Value;

@Value
public class CreateHotelResponse {

    private String id;
    private String name;
    private String location;  

    public static CreateHotelResponse from(Hotel hotel) {
        return new CreateHotelResponse(
                hotel.getId().toString(),
                hotel.getName(),
                hotel.getLocation()
        );
    }
}
