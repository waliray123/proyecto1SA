package com.eatsleep.reservation.reservation.infrastructure.outputadapters.restapi;

import com.eatsleep.reservation.reservation.application.makereservation.RoomReservationResponse;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.CheckExistClientOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.GetInformationRoomOutputPort;
import org.springframework.http.ResponseEntity;

public class ReservationRestApiOutputAdapter implements CheckExistHotelOutputPort
    ,GetInformationRoomOutputPort,CheckExistClientOutputPort{
    
    private final RestTemplate restTemplate;

    public ReservationRestApiOutputAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean checkHotelExistsOutputPort(String idHotel) {
        String url = "lb://HOTEL/v1/hotels/exists/" + idHotel;
        try {
            restTemplate.headForHeaders(url);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            } else {
                throw e;
            }
        }
    }


    @Override
    public RoomReservationResponse getRoomInformation(String idRoom) {
        String url = "lb://HOTEL/v1/rooms/getroom/" + idRoom;
        try {
            ResponseEntity<RoomReservationResponse> response = restTemplate.getForEntity(url, RoomReservationResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null; 
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean checkClientExistsOutputPort(String idClient) {
        String url = "lb://USER/v1/clients/exists/" + idClient;
        try {
            restTemplate.headForHeaders(url);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            } else {
                throw e;
            }
        }
    }


}
