package com.eatsleep.hotel.hotel.application.createhotelusecase;

import lombok.Value;

@Value
public class CreateHotelRequest {
    private String name;
    private String location;  
}
