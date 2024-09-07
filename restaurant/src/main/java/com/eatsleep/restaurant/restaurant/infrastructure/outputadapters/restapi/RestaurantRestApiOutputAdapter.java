package com.eatsleep.restaurant.restaurant.infrastructure.outputadapters.restapi;

import com.eatsleep.restaurant.restaurant.infrastructure.outputports.restapi.ExistHotelOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RestaurantRestApiOutputAdapter implements ExistHotelOutputPort {
    private final RestTemplate restTemplate;

    public RestaurantRestApiOutputAdapter(RestTemplate restTemplate) {
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
}
