package com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.hotel.domain.Hotel;
import java.util.UUID;
import lombok.Value;

@Value
public class RetrieveHotelResponse {

    private String id;
    private String name;
    private String location;  

    public static RetrieveHotelResponse from(Hotel hotel) {
        return new RetrieveHotelResponse(
                hotel.getId().toString(),
                hotel.getName(),
                hotel.getLocation()
        );
    }
}
