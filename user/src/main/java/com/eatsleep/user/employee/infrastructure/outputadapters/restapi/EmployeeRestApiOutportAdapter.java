package com.eatsleep.user.employee.infrastructure.outputadapters.restapi;

import com.eatsleep.user.common.OutputAdapter;
import com.eatsleep.user.employee.infrastructure.outputports.restapi.ExistHotelOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.eatsleep.user.employee.infrastructure.outputports.restapi.ExistRestaurantOutputPort;


@OutputAdapter
public class EmployeeRestApiOutportAdapter implements ExistHotelOutputPort,ExistRestaurantOutputPort  {

    private final RestTemplate restTemplate;

    public EmployeeRestApiOutportAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean validateExistHotel(String idHotel) {
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
    public boolean validateExistRestaurant(String idRestaurant) {
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
}
