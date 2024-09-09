package com.eatsleep.reservation.reservation.infrastructure.outputadapters.restapi;

import com.eatsleep.reservation.common.OutputAdapter;
import com.eatsleep.reservation.reservation.application.makereservation.RoomReservationResponse;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.CheckExistClientOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.FindPromotionByProductAndDateOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.eatsleep.reservation.reservation.infrastructure.outputports.restapi.GetInformationRoomOutputPort;
import java.time.LocalDate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@OutputAdapter
public class ReservationRestApiOutputAdapter implements CheckExistHotelOutputPort
    ,GetInformationRoomOutputPort,CheckExistClientOutputPort
    ,FindPromotionByProductAndDateOutputPort {
    
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
    
    @Override
    public Double findPromotionByProductAndDate(String idProduct, LocalDate date) {
        String url = "lb://PROMOTION/v1/promotions/product/" + idProduct + "/date/" + date.toString();

        try {
            ResponseEntity<Double> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<Double>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // Devuelve el valor de la promoción
            } else {
                return null;  // Si no hay una promoción, devuelve null o lanza una excepción si lo prefieres
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;  // No se encontró la promoción
            } else {
                throw e;  // Lanza la excepción para otros errores HTTP
            }
        }
    }


}
