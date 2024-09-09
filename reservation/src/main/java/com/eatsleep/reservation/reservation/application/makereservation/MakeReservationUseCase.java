package com.eatsleep.reservation.reservation.application.makereservation;

import com.eatsleep.reservation.common.UseCase;
import com.eatsleep.reservation.reservation.application.exceptions.ReservationException;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.domain.Status;
import com.eatsleep.reservation.reservation.infrastructure.inputports.MakeReservationInputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputadapters.db.ReservationDbOutputAdapter;
import com.eatsleep.reservation.reservation.infrastructure.outputadapters.restapi.ReservationRestApiOutputAdapter;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db.ReservationDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class MakeReservationUseCase implements MakeReservationInputPort{
    
    private ReservationDbOutputAdapter reservationDbOutputAdapter;
    private ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter;
    private ReservationRestApiOutputAdapter reservationRestApiOutputAdapter;

    @Autowired
    public MakeReservationUseCase(ReservationDbOutputAdapter reservationDbOutputAdapter
            , ReservationDescriptionDbOutputAdapter reservationDescriptionDbOutputAdapter
            , ReservationRestApiOutputAdapter reservationRestApiOutputAdapter) {
        this.reservationDbOutputAdapter = reservationDbOutputAdapter;
        this.reservationDescriptionDbOutputAdapter = reservationDescriptionDbOutputAdapter;
        this.reservationRestApiOutputAdapter = reservationRestApiOutputAdapter;
    }

    @Override
    public Reservation makeReservation(MakeReservationRequest makeReservationRequest, String idUser, String idHotel) throws ReservationException{
        if (idUser == null) {
            throw new IllegalArgumentException("El usuario es obligatorio al realizar una reservacion");
        }
        if (idHotel == null) {
            throw new IllegalArgumentException("El hotel es un campo obligatorio al realizar una reservacion");
        }
        
        // Validar que el usuario existe
        validateClient(idUser);
        // Validar que el hotel exista
        validateHotel(idHotel);
        
        //  Generar la reservacion
        Reservation reservation = Reservation.builder()
                .idLocation(UUID.fromString(idHotel))
                .dateStart(makeReservationRequest.getDateStart())
                .dateEnd(makeReservationRequest.getDateEnd())
                .status(Status.PENDING)
                .user(UUID.fromString(idUser))
                .build()
                ;
        
        // Calcular la cantidad de dias que se quedaran
        long daysBeetween = calculateDaysBeetweenTwoDates(
                makeReservationRequest.getDateStart(),
                makeReservationRequest.getDateEnd());
        
        List<ReservationDescription> roomsValidated = validateRooms(idHotel, makeReservationRequest.getIdRooms(),daysBeetween,reservation);
        
        
        // Calcular el total del costo de todo
        reservation.setTotal(calculateTotalPriceByReservationsDescriptions(roomsValidated)); 
        
        // Guardar reservacion y descripciones
        reservation = this.reservationDbOutputAdapter.makeReservation(reservation);

        
        for (ReservationDescription reservationDescription : roomsValidated) {                        
            reservationDescription.setReservation(reservation);
            reservationDescription = this.reservationDescriptionDbOutputAdapter.saveReservationDescription(reservationDescription);
        }
        
        return reservation;
        
    }
    
    private void validateHotel(String idHotel){
        if(!reservationRestApiOutputAdapter.checkHotelExistsOutputPort(idHotel)){
            throw new IllegalArgumentException("El identificador " + idHotel +" no corresponde a un hotel");
        }
    }
    
    private void validateClient(String idClient){
        if(!reservationRestApiOutputAdapter.checkClientExistsOutputPort(idClient)){
            throw new IllegalArgumentException("El identificador"+ idClient +"no corresponde a un cliente");
        }
    }
    
    private List<ReservationDescription> validateRooms(String idHotel, List<String> idRooms, Long daysBeetween, Reservation reservation) throws ReservationException{
        List<ReservationDescription> reservations = new ArrayList<>();
        for (String idRoom : idRooms) {
            // Revisar que el cuarto exista
            RoomReservationResponse roomReservationResponse = this.reservationRestApiOutputAdapter.getRoomInformation(idRoom);
            if (roomReservationResponse == null) {
                throw new ReservationException("Cuarto con id: " + idRoom + " es inexistente");
            }
            
            // Revisar que el cuarto es del hotel que se agrego
            if (roomReservationResponse.getIdHotel() == idHotel) {
                throw new ReservationException("Cuarto con id: " + idRoom + " no pertenece al mismo hotel de la reservacion");
            }
            
            Optional<ReservationDescription> reservationDescriptionOptionalStartDate 
                    = this.reservationDescriptionDbOutputAdapter.findReservationByRoomAndDatePendingConfirmed(idRoom, reservation.getDateStart());
            if (reservationDescriptionOptionalStartDate.isPresent()) {
                throw new ReservationException("No se puede reservar el cuarto numero porque ya tiene un reservacion en la fecha: " + reservation.getDateStart());
            } 
            
            // Revisar si hay una promocion relacionada
            double promotion = 0;
            
            try {
                promotion = this.reservationRestApiOutputAdapter.findPromotionByProductAndDate(idRoom,reservation.getDateStart());
            } catch (Exception e) {
                promotion = 0;
                System.out.println("No hay promocion");
            }
            
            double unitPrice = roomReservationResponse.getUnitPrice() - roomReservationResponse.getUnitPrice() * promotion/100;
            
            // Agregar las reservaciones validas
            reservations.add(generateReservationDescription(idRoom,unitPrice,Math.toIntExact(daysBeetween),reservation));
        }
        return reservations;
    }
    
    private ReservationDescription generateReservationDescription(String idRoom, double unitPrice,int daysBeetween, Reservation reservation){
        ReservationDescription reservationDescription = ReservationDescription.builder()
                .idProduct(UUID.fromString(idRoom))
                .unitPrice(unitPrice)
                .quantity(daysBeetween)
                .reservation(reservation)
                .build();
        return reservationDescription;
    }
    
    private double calculateTotalPriceByReservationsDescriptions(List<ReservationDescription> reservationsDescriptions){
        double total = 0.00;
        for (ReservationDescription reservationDescription : reservationsDescriptions) {
            total +=(reservationDescription.getQuantity() * reservationDescription.getUnitPrice());
        }
        return total;
    }
    
    private long calculateDaysBeetweenTwoDates(LocalDate startDate, LocalDate endDate){
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    

}
