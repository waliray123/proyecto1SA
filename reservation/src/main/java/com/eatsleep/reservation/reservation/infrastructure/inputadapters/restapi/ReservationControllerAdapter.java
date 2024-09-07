package com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi;

import com.eatsleep.reservation.common.WebAdapter;
import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.application.makereservation.MakeReservationRequest;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi.response.GetReservationAllDescriptionResponse;
import com.eatsleep.reservation.reservation.infrastructure.inputadapters.restapi.response.MakeReservationResponse;
import com.eatsleep.reservation.reservation.infrastructure.inputports.GetReservationByIdReservationInputPort;
import com.eatsleep.reservation.reservation.infrastructure.inputports.GetReservationsByUserHotelInputPort;
import com.eatsleep.reservation.reservation.infrastructure.inputports.MakeReservationInputPort;
import com.eatsleep.reservation.reservation.infrastructure.inputports.SetStatusReservationInputPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/reservations")
@WebAdapter
public class ReservationControllerAdapter {
    private MakeReservationInputPort makeReservationInputPort;
    private GetReservationsByUserHotelInputPort getReservationsByUserHotelInputPort;
    private GetReservationByIdReservationInputPort getReservationByIdReservationInputPort;
    private SetStatusReservationInputPort setStatusReservationInputPort;
    
    @Autowired
    public ReservationControllerAdapter(MakeReservationInputPort makeReservationInputPort
            , GetReservationsByUserHotelInputPort getReservationsByUserHotelInputPort
            , GetReservationByIdReservationInputPort getReservationByIdReservationInputPort
            , SetStatusReservationInputPort setStatusReservationInputPort) {
        this.makeReservationInputPort = makeReservationInputPort;
        this.getReservationsByUserHotelInputPort = getReservationsByUserHotelInputPort;
        this.getReservationByIdReservationInputPort = getReservationByIdReservationInputPort;
        this.setStatusReservationInputPort = setStatusReservationInputPort;
    }
    
    @PostMapping("/{idUser}/{idHotel}")
    public ResponseEntity<MakeReservationResponse> createReservation (
            @PathVariable String idUser, 
            @PathVariable String idHotel, 
            @RequestBody MakeReservationRequest reservation) throws ReservationException{
        MakeReservationResponse createdReservation = new MakeReservationResponse(makeReservationInputPort.makeReservation(reservation,idUser,idHotel));
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> pruebaReservation() throws ReservationException{
        return new ResponseEntity<>("Devolver una prueba de reservation", HttpStatus.CREATED);
    }
    
    @GetMapping("/{idUser}/{idHotel}")
    public ResponseEntity<List<GetReservationAllDescriptionResponse>> getByUserIdAndHotelReservation (
            @PathVariable String idUser, 
            @PathVariable String idHotel) throws ReservationException{
        List<GetReservationAllDescriptionResponse> reservationsResponse = new ArrayList<>();
        List<Reservation> reservations = this.getReservationsByUserHotelInputPort.getReservationByUserHotel(idUser, idHotel);
        for (Reservation reservation : reservations) {
            GetReservationAllDescriptionResponse reservationResponse = new GetReservationAllDescriptionResponse(reservation);
            reservationsResponse.add(reservationResponse);
        }
        return new ResponseEntity<>(reservationsResponse, HttpStatus.FOUND);
    }
    
    @GetMapping("/reservation/{idReservation}")
    public ResponseEntity<GetReservationAllDescriptionResponse> getByUserIdAndHotelReservation (
            @PathVariable String idReservation) throws ReservationException{
        Optional<Reservation> reservationOptional = this.getReservationByIdReservationInputPort.getReservationByIdReservation(idReservation);
        if(reservationOptional.isPresent()){
            GetReservationAllDescriptionResponse response = new GetReservationAllDescriptionResponse(reservationOptional.get());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/status/{idReservation}/{status}")
    public ResponseEntity<Boolean> setStatusReservation (@PathVariable String idReservation, @PathVariable String status){
        if(this.setStatusReservationInputPort.setStatusReservation(idReservation, status)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
    

}
