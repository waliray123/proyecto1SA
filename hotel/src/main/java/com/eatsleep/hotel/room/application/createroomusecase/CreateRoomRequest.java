package com.eatsleep.hotel.room.application.createroomusecase;

import com.eatsleep.hotel.hotel.application.createhotelusecase.*;
import lombok.Value;

@Value
public class CreateRoomRequest {    
    private int number;
    private double unitPrice;
    private int floor;
    private double maintenance;
    private String description;
    private String idHotel;
}
