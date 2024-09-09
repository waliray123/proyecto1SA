package com.eatsleep.reports.infrastructure.outputadapters.restapi;

import com.eatsleep.reports.application.allearningscostsusecase.BillResponse;
import com.eatsleep.reports.application.allearningscostsusecase.MaintenanceResponse;
import com.eatsleep.reports.application.allearningscostsusecase.PaymentResponse;
import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import com.eatsleep.reports.common.OutputAdapter;
import com.eatsleep.reports.infrastructure.outputports.restapi.FindAllMaintenancesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.FindPaymentsBetweenDatesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.FindTopRestaurantBillsOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.FindTopRoomOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.GetAllBillsByDatesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.GetBillsDescriptionByClientByDatesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.GetBillsDescriptionByClientIdLocationByDatesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.GetBillsDescriptionByIdLocationByDatesOutputPort;
import com.eatsleep.reports.infrastructure.outputports.restapi.GetAllEmployeesByIdLocationOutputPort;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@OutputAdapter
public class ReportsRestApiOutputAdapter implements GetAllEmployeesByIdLocationOutputPort, FindPaymentsBetweenDatesOutputPort
,FindAllMaintenancesOutputPort,FindTopRestaurantBillsOutputPort,GetAllBillsByDatesOutputPort,FindTopRoomOutputPort,GetBillsDescriptionByClientByDatesOutputPort
,GetBillsDescriptionByClientIdLocationByDatesOutputPort
,GetBillsDescriptionByIdLocationByDatesOutputPort



{
    
    private final RestTemplate restTemplate;

    public ReportsRestApiOutputAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public List<RetrieveEmployeeResponse> getAllEmployeesByIdLocation(String idLocation) {
        String url = "lb://USER/v1/employees/all/" + idLocation;
        try {
            ResponseEntity<List<RetrieveEmployeeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RetrieveEmployeeResponse>>() {}
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
    
    
    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByIdLocationByDates(String idLocation, LocalDate startDate, LocalDate endDate) {
        String url = "lb://BILL/v1/bills/rangelocation/" + idLocation;

        // Crear los parámetros de la solicitud
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();

        try {
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            } else {
                throw e;
            }
        }
    }
    
    
    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByClientByDates(String idClient, LocalDate startDate, LocalDate endDate) {
        String url = "lb://BILL/v1/bills/rangeclient/" + idClient;

        // Crear los parámetros de la solicitud
        /// Construir la URI con parámetros de consulta
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();

        try {
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList(); // Lista vacía si no hay resultados
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByClientIdLocationByDates(
        String idClient, String idLocation, LocalDate startDate, LocalDate endDate) {

        String url = "lb://BILL/v1/bills/rangeclient/" + idClient + "/" + idLocation;

        // Construir la URI con parámetros de consulta
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();

        try {
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList(); // Lista vacía si no hay resultados
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<BillResponse> getAllBillsByDates(LocalDate startDate, LocalDate endDate) {
        String url = "lb://BILL/v1/bills/rangeall";

        /// Construir la URI con parámetros de consulta
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();

        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<BillResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BillResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<TopsBillDescriptionResponse> findTopRoom() {
        String url = "lb://BILL/v1/bills/topRoom";

        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<TopsBillDescriptionResponse> findTopRoomBillsByIdLocation(String idLocation) {
        // Construimos la URL con el idLocation
        String url = "lb://BILL/v1/bills/topRoomByLocation/" + idLocation;

        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<TopsBillDescriptionResponse> findTopRestaurantBills() {
        // Construimos la URL para el endpoint topRestaurant
        String url = "lb://BILL/v1/bills/topRestaurant";

        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<TopsBillDescriptionResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TopsBillDescriptionResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public List<MaintenanceResponse> findAllMaintenances(LocalDate startDate, LocalDate endDate) {
        // Construimos la URL para el endpoint allmaintenances
        String url = "lb://HOTEL/v1/hotels/allmaintenances";

        /// Construir la URI con parámetros de consulta
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();


        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<MaintenanceResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MaintenanceResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }

    
    @Override
    public List<PaymentResponse> findPaymentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        // Construimos la URL para el endpoint rangepayments
        String url = "lb://USER/v1/employees/rangepayments";

        /// Construir la URI con parámetros de consulta
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("startDate", startDate != null ? startDate.toString() : "")
                .queryParam("endDate", endDate != null ? endDate.toString() : "");

        // Construir la URI final
        URI finalUri = uriBuilder.build().toUri();

        try {
            // Ejecutamos la solicitud GET
            ResponseEntity<List<PaymentResponse>> response = restTemplate.exchange(
                finalUri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaymentResponse>>() {}
            );

            // Verificamos si la respuesta fue exitosa
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList(); // Retornar lista vacía si no hay resultados
            }
        } catch (HttpClientErrorException e) {
            // Si el código es 404, retornamos una lista vacía, si no lanzamos la excepción
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw e;
            }
        }
    }


}
