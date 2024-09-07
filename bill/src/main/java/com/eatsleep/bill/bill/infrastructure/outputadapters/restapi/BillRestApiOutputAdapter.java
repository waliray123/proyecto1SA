package com.eatsleep.bill.bill.infrastructure.outputadapters.restapi;

import com.eatsleep.bill.bill.application.payhotelbillusecase.GetReservationAllDescriptionResponse;
import com.eatsleep.bill.bill.application.payhotelbillusecase.RoomBillResponse;
import com.eatsleep.bill.bill.application.payrestaurantbillusecase.DishBillResponse;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistClientOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistRestaurantOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationDishOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationReservationOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationRoomOutputPort;
import org.springframework.http.ResponseEntity;

public class BillRestApiOutputAdapter implements CheckExistHotelOutputPort
    ,GetInformationRoomOutputPort,CheckExistClientOutputPort
    ,GetInformationDishOutputPort, CheckExistRestaurantOutputPort
    ,GetInformationReservationOutputPort{
    
    private final RestTemplate restTemplate;

    public BillRestApiOutputAdapter(RestTemplate restTemplate) {
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
    public RoomBillResponse getRoomInformation(String idRoom) {
        String url = "lb://HOTEL/v1/rooms/getroom/" + idRoom;
        try {
            ResponseEntity<RoomBillResponse> response = restTemplate.getForEntity(url, RoomBillResponse.class);
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
    public boolean checkRestaurantExistsOutputPort(String idRestaurant) {
        String url = "lb://RESTAURANT/v1/restaurants/exists/" + idRestaurant;
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
    public DishBillResponse getDishInformation(String idDish) {
        String url = "lb://RESTAURANT/v1/dishes/getdish/" + idDish;
        try {
            ResponseEntity<DishBillResponse> response = restTemplate.getForEntity(url, DishBillResponse.class);
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
    public GetReservationAllDescriptionResponse getReservationInformation(String idReservation) {
        String url = "lb://RESERVATION/v1/reservations/reservation/" + idReservation;
        try {
            ResponseEntity<GetReservationAllDescriptionResponse> response = restTemplate.getForEntity(url, GetReservationAllDescriptionResponse.class);
            if (response.getStatusCode() == HttpStatus.FOUND) {
                return response.getBody(); // Devolver la respuesta si es encontrada
            } else {
                return null; // Retornar null si no es encontrada
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null; // Si la reserva no es encontrada
            } else {
                throw e; // Propagar la excepción para otros errores HTTP
            }
        }
    }


}
