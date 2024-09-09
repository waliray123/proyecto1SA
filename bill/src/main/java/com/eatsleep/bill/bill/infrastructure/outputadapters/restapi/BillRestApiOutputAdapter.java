package com.eatsleep.bill.bill.infrastructure.outputadapters.restapi;

import com.eatsleep.bill.bill.application.payhotelbillusecase.GetReservationAllDescriptionResponse;
import com.eatsleep.bill.bill.application.payhotelbillusecase.RoomBillResponse;
import com.eatsleep.bill.bill.application.payrestaurantbillusecase.DishBillResponse;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistClientOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistHotelOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.CheckExistRestaurantOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.FindPromotionByProductAndDateOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationDishOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationReservationOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.GetInformationRoomOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.SetStatusReservationOutputAdapter;
import com.eatsleep.bill.bill.infrastructure.outputports.restapi.SetcheckinOutputAdapter;
import com.eatsleep.bill.common.OutputAdapter;
import java.time.LocalDate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@OutputAdapter
public class BillRestApiOutputAdapter implements CheckExistHotelOutputPort
    ,GetInformationRoomOutputPort,CheckExistClientOutputPort
    ,GetInformationDishOutputPort, CheckExistRestaurantOutputPort
    ,GetInformationReservationOutputPort
    ,FindPromotionByProductAndDateOutputPort
    ,SetcheckinOutputAdapter
    ,SetStatusReservationOutputAdapter{
    
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

    @Override
    public boolean setCheckInRoom(String idRoom) {
        String url = "lb://HOTEL/v1/rooms/checkin/" + idRoom;

        try {
            // Realiza una solicitud POST al microservicio para marcar la habitación como "check-in"
            ResponseEntity<Boolean> response = restTemplate.exchange(
                url, 
                HttpMethod.PUT,
                null, 
                new ParameterizedTypeReference<Boolean>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // Devuelve true si el check-in fue exitoso
            } else {
                return false;  // Si no fue exitoso, devuelve false
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;  // La habitación no fue encontrada
            } else {
                throw e;  // Lanza la excepción para otros errores HTTP
            }
        }
    }

    @Override
    public boolean setStatusConfirmed(String idReservation) {
        String url = "lb://RESERVATION/v1/reservations/status/" + idReservation + "/confirmed";

        try {
            // Realiza una solicitud POST al microservicio para actualizar el estado de la reserva
            ResponseEntity<Boolean> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                null, 
                new ParameterizedTypeReference<Boolean>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();  // Devuelve true si el estado fue actualizado exitosamente
            } else {
                return false;  // Si no fue exitoso, devuelve false
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;  // La reserva no fue encontrada
            } else {
                throw e;  // Lanza la excepción para otros errores HTTP
            }
        }
    }


}
