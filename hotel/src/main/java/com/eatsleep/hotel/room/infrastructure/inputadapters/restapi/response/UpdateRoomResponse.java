package com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response;

import com.eatsleep.hotel.room.domain.Room;
import lombok.Value;

@Value
public class UpdateRoomResponse {

    private String id;
    private int number;
    private double unitPrice;
    private int floor;
    private double maintenance;
    private String description;
    private boolean status;
    private String idHotel; 

    public UpdateRoomResponse(Room room) {
        this.id = room.getId().toString();
        this.number = room.getNumber();
        this.unitPrice = room.getUnitPrice();
        this.floor = room.getFloor();
        this.maintenance = room.getMaintenance();
        this.description = room.getDescription();
        this.status = room.isOccupied();
        this.idHotel = room.getHotel().getId().toString();
    }

    
}
