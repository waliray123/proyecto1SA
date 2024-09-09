package com.eatsleep.promotion.comment.infrastructure.outputadapters.restapi;

import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.CheckExistClientOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.CheckExistDishOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.CheckExistRestaurantOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.CheckExistRoomOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.GetInformationDishOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.GetInformationRoomOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.restapi.GetToSpendingClientsOutputPort;
import com.eatsleep.promotion.common.OutputAdapter;
import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.DishPromotionResponse;
import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.RoomPromotionResponse;
import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.TopSpendingClientResponse;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@OutputAdapter
public class CommentRestApiOutputAdapter implements CheckExistHotelOutputPort, CheckExistRestaurantOutputPort
    ,CheckExistRoomOutputPort,CheckExistDishOutputPort,CheckExistClientOutputPort
    ,GetInformationRoomOutputPort
    ,GetInformationDishOutputPort
    ,GetToSpendingClientsOutputPort{
    
    private final RestTemplate restTemplate;

    public CommentRestApiOutputAdapter(RestTemplate restTemplate) {
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
    public RoomPromotionResponse getRoomInformation(String idRoom) {
        String url = "lb://HOTEL/v1/rooms/getroom/" + idRoom;
        try {
            ResponseEntity<RoomPromotionResponse> response = restTemplate.getForEntity(url, RoomPromotionResponse.class);
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
    public DishPromotionResponse getDishInformation(String idDish) {
        String url = "lb://RESTAURANT/v1/dishes/getdish/" + idDish;
        try {
            ResponseEntity<DishPromotionResponse> response = restTemplate.getForEntity(url, DishPromotionResponse.class);
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
    public List<TopSpendingClientResponse> findToSpendingClients() {
        String url = "lb://BILL/v1/bills/clientsMoreSpending";
        try {
            ResponseEntity<List<TopSpendingClientResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopSpendingClientResponse>>() {}
            );

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

}
