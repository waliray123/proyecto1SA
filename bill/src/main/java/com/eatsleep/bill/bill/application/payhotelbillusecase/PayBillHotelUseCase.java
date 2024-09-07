package com.eatsleep.bill.bill.application.payhotelbillusecase;

import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillHotelInputPort;
import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbOutputAdapter;
import com.eatsleep.bill.bill.infrastructure.outputadapters.restapi.BillRestApiOutputAdapter;
import com.eatsleep.bill.billdescription.application.paybillusecase.PayBillDescriptionRequest;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class PayBillHotelUseCase implements PayBillHotelInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillRestApiOutputAdapter billRestApiOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public PayBillHotelUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter
            , BillRestApiOutputAdapter billRestApiOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
        this.billRestApiOutputAdapter = billRestApiOutputAdapter;
    }

    @Override
    public Bill makeBill(PayBillHotelRequest billRequest, String idUser, String idHotel) throws BillException{
        // Validar los datos del request
        validateRequest(billRequest, idUser, idHotel);
        
        // Validar que el usuario existe
        validateClient(idUser);
        
        // Validar que el hotel exista
        validateHotel(idHotel);
        
        //  Generar la factura
        Bill bill = Bill.builder()
                .type("HOTEL")
                .idLocation(UUID.fromString(idHotel))
                .startDate(billRequest.getStartDate())
                .endDate(billRequest.getEndDate())
                .userId(UUID.fromString(idUser)) 
                .build();
        // Falta agregar total, id, 
        
        // Validar si tiene reservacion
        if(billRequest.getReservationId() != null && billRequest.getReservationId().isEmpty() == false){
            // Si tiene reservacion hacer todo lo siguiente por reservacion
            // Traer la reservacion y sus descripciones
            // Convertirlas en factura
            // Guardar
            GetReservationAllDescriptionResponse reservation = this.billRestApiOutputAdapter.getReservationInformation(idUser);
            if(reservation == null){
                throw new IllegalArgumentException("El identificador de la reservacion es incorrecto o no devuelve informacion");
            }
            if (!reservation.getDateStart().equals(bill.getStartDate())) {
                throw new BillException("La fecha de inicio de la reserva no coincide con la fecha de inicio de la factura.");
            }
            if (!reservation.getDateEnd().equals(bill.getEndDate())) {
                throw new BillException("La fecha de final de la reserva no coincide con la fecha final de la factura.");
            }
            if (!reservation.getUser().equals(bill.getUserId())) {
                throw new BillException("El usuario de la reservacion debe ser el mismo que factura");
            }
            if (!reservation.getIdLocation().equalsIgnoreCase(idHotel)) {
                throw new BillException("El de la reservacion debe ser el mismo con el que se factura");
            }
            long daysBeetween = calculateDaysBeetweenTwoDates(
                    billRequest.getStartDate(),
                    billRequest.getEndDate());
            List<BillDescription> roomsValidated = validateRoomsReservation(idHotel, reservation.getDescriptions(),daysBeetween,bill);

            // Calcular el total del costo de todo
            bill.setTotal(calculateTotalPriceByBillsDescriptions(roomsValidated)); 

            // Guardar reservacion y descripciones
            bill = this.billDbOutputAdapter.payHotelBill(bill);


            for (BillDescription billDescription : roomsValidated) {                        
                billDescription.setBill(bill);
                billDescription = this.billDescriptionDbOutputAdapter.saveBillDescription(billDescription);
            }
            
        }else{
            // Calcular la cantidad de dias que se quedaran
            long daysBeetween = calculateDaysBeetweenTwoDates(
                    billRequest.getStartDate(),
                    billRequest.getEndDate());

            List<BillDescription> roomsValidated = validateRooms(idHotel, billRequest.getDescription_products(),daysBeetween,bill);


            // Calcular el total del costo de todo
            bill.setTotal(calculateTotalPriceByBillsDescriptions(roomsValidated)); 

            // Guardar reservacion y descripciones
            bill = this.billDbOutputAdapter.payHotelBill(bill);


            for (BillDescription billDescription : roomsValidated) {                        
                billDescription.setBill(bill);
                billDescription = this.billDescriptionDbOutputAdapter.saveBillDescription(billDescription);
            }
        }

        return bill;
        
    }
    
    private void validateRequest(PayBillHotelRequest request, String idUser, String idLocation){
        if (idLocation == null || idLocation.isEmpty()) {
            throw new IllegalArgumentException("El lugar (hotel) es obligatorio");
        }
        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("La fecha es necesaria para la transaccion");
        }
        if (request.getEndDate() == null) {
            throw new IllegalArgumentException("La fecha es necesaria para la transaccion");
        }
        if (idUser == null || idUser.isEmpty()) {
            throw new IllegalArgumentException("El campo id del usuario es obligatorio");
        }
        if (!request.getStartDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio debe ser la fecha actual");
        }
    }
    
    private void validateClient(String idClient){
        if(!billRestApiOutputAdapter.checkClientExistsOutputPort(idClient)){
            throw new IllegalArgumentException("El identificador"+ idClient +"no corresponde a un cliente");
        }
    }
    
    private void validateHotel(String idHotel){
        if(!billRestApiOutputAdapter.checkHotelExistsOutputPort(idHotel)){
            throw new IllegalArgumentException("El identificador " + idHotel +" no corresponde a un hotel");
        }
    }
    
    private List<BillDescription> validateRooms(String idHotel, List<PayBillDescriptionRequest> roomsRequest, Long daysBeetween, Bill bill) throws BillException{
        List<BillDescription> bills = new ArrayList<>();
        for (PayBillDescriptionRequest roomRequest : roomsRequest) {
            // Revisar que el cuarto exista
            RoomBillResponse roomBillResponse = billRestApiOutputAdapter.getRoomInformation(roomRequest.getIdProduct());
            if (roomBillResponse == null) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " es inexistente");
            }
            if (roomBillResponse.isOccupied()) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " ya esta ocupado");
            }
            
            // Revisar que el cuarto es del hotel que se agrego
            if (roomBillResponse.getIdHotel() == idHotel) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " no pertenece al mismo hotel de la reservacion");
            }
            
            // TODO: Revisar si hay una promocion relacionada
            double promotion = 0;
            double unitPrice = roomBillResponse.getUnitPrice() + promotion;
            
            // Agregar las reservaciones validas
            bills.add(generateBillDescription(roomRequest.getIdProduct(),unitPrice,Math.toIntExact(daysBeetween),bill));
        }
        return bills;
    }
    
    private List<BillDescription> validateRoomsReservation(String idHotel, List<GetReservationDescriptionResponse> roomsRequest, Long daysBeetween, Bill bill) throws BillException{
        List<BillDescription> bills = new ArrayList<>();
        for (GetReservationDescriptionResponse roomRequest : roomsRequest) {
            // Revisar que el cuarto exista
            RoomBillResponse roomBillResponse = billRestApiOutputAdapter.getRoomInformation(roomRequest.getIdProduct());
            if (roomBillResponse == null) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " es inexistente");
            }
            if (roomBillResponse.isOccupied()) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " ya esta ocupado");
            }
            
            // Revisar que el cuarto es del hotel que se agrego
            if (roomBillResponse.getIdHotel() == idHotel) {
                throw new BillException("Cuarto con id: " + roomRequest.getIdProduct() + " no pertenece al mismo hotel de la reservacion");
            }
            
            // TODO: Revisar si hay una promocion relacionada
            double promotion = 0;
            double unitPrice = roomBillResponse.getUnitPrice() + promotion;
            
            // Agregar las reservaciones validas
            bills.add(generateBillDescription(roomRequest.getIdProduct(),unitPrice,Math.toIntExact(daysBeetween),bill));
        }
        return bills;
    }
    
    private BillDescription generateBillDescription(String idRoom, double unitPrice,int daysBeetween, Bill bill){
        BillDescription billDescription = BillDescription.builder()
                .idProduct(UUID.fromString(idRoom))
                .unitPrice(unitPrice)
                .quantity(daysBeetween)
                .bill(bill)
                .build();
        return billDescription;
    }
    
    private double calculateTotalPriceByBillsDescriptions(List<BillDescription> billsDescriptions){
        double total = 0.00;
        for (BillDescription billDescription : billsDescriptions) {
            total +=(billDescription.getQuantity() * billDescription.getUnitPrice());
        }
        return total;
    }
    
    private long calculateDaysBeetweenTwoDates(LocalDate startDate, LocalDate endDate){
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

}
