package com.eatsleep.promotion.promotion.infrastructure.outputadapters.restapi;

import com.eatsleep.promotion.promotion.infrastructure.outputports.restapi.CheckExistDishOutputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputports.restapi.CheckExistRestaurantOutputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputports.restapi.CheckExistRoomOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class PromotionRestApiOutputAdapter implements CheckExistHotelOutputPort, CheckExistRestaurantOutputPort
    ,CheckExistRoomOutputPort,CheckExistDishOutputPort{
    
    private final RestTemplate restTemplate;

    public PromotionRestApiOutputAdapter(RestTemplate restTemplate) {
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
    public boolean checkRoomExistsOutputPort(String idRoom) {
        String url = "lb://HOTEL/v1/rooms/exists/" + idRoom;
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
    public boolean checkDishExistsOutputPort(String idDish) {
        String url = "lb://RESTAURANT/v1/dishes/exists/" + idDish;
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
