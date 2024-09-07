package com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.UUID;
import lombok.Value;

@Value
public class UpdateHotelResponse {

    private String id;
    private String name;
    private String location;  

    public static UpdateHotelResponse from(Hotel hotel) {
        return new UpdateHotelResponse(
                hotel.getId().toString(),
                hotel.getName(),
                hotel.getLocation()
        );
    }
}
