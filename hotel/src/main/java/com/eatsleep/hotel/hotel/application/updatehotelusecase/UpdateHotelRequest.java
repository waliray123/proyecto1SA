package com.eatsleep.hotel.hotel.application.updatehotelusecase;

import com.eatsleep.hotel.hotel.application.createhotelusecase.*;
import lombok.Value;

@Value
public class UpdateHotelRequest {
    private String name;
    private String location;  
}
