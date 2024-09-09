package com.eatsleep.bill.bill.application.payrestaurantbillusecase;

import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillRestaurantInputPort;
import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbOutputAdapter;
import com.eatsleep.bill.bill.infrastructure.outputadapters.restapi.BillRestApiOutputAdapter;
import com.eatsleep.bill.billdescription.application.paybillusecase.PayBillDescriptionRequest;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.billdescription.infrastructure.outputadapters.db.BillDescriptionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class PayBillRestaurantUseCase implements PayBillRestaurantInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;
    private BillRestApiOutputAdapter billRestApiOutputAdapter;
    private BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter;

    @Autowired
    public PayBillRestaurantUseCase(BillDbOutputAdapter billDbOutputAdapter
            , BillDescriptionDbOutputAdapter billDescriptionDbOutputAdapter
            , BillRestApiOutputAdapter billRestApiOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
        this.billDescriptionDbOutputAdapter = billDescriptionDbOutputAdapter;
        this.billRestApiOutputAdapter = billRestApiOutputAdapter;
    }
    
    @Override
    public Bill makeBill(PayBillRestaurantRequest billRequest, String idUser, String idRestaurant) throws BillException {
        // Validar los datos del request
        validateRequest(billRequest, idUser, idRestaurant);
        
        // Validar que el usuario existe
        validateClient(idUser);
        
        // Validar que el restaurante exista
        validateRestaurant(idRestaurant);
        
        //  Generar la factura
        Bill bill = Bill.builder()
                .type("RESTAURANT")
                .idLocation(UUID.fromString(idRestaurant))
                .startDate(billRequest.getStartDate())
                .endDate(billRequest.getStartDate())
                .userId(UUID.fromString(idUser)) 
                .build();

        List<BillDescription> dishesValidated = validateDishes(idRestaurant, billRequest.getDescription_products(),bill);

        // Calcular el total del costo de todo
        bill.setTotal(calculateTotalPriceByBillsDescriptions(dishesValidated)); 

        // Guardar reservacion y descripciones
        bill = this.billDbOutputAdapter.payRestaurantBill(bill);

        List<BillDescription> dishesSaved = new ArrayList<>();
        for (BillDescription billDescription : dishesValidated) {
            billDescription.setBill(bill);
            BillDescription billSaved = this.billDescriptionDbOutputAdapter.saveBillDescription(billDescription);
            dishesSaved.add(billSaved);
        }
        bill.setDescriptions(dishesSaved);
        
        return bill;
    }
    
    private void validateRequest(PayBillRestaurantRequest request, String idUser, String idLocation){
        if (idLocation == null || idLocation.isEmpty()) {
            throw new IllegalArgumentException("El lugar (restaurante) es obligatorio");
        }
        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("La fecha es necesaria para la transaccion");
        }
        if (idUser == null || idUser.isEmpty()) {
            throw new IllegalArgumentException("El campo id del usuario es obligatorio");
        }
    }
    
    private void validateClient(String idClient){
        if(!billRestApiOutputAdapter.checkClientExistsOutputPort(idClient)){
            throw new IllegalArgumentException("El identificador"+ idClient +"no corresponde a un cliente");
        }
    }
    
    private void validateRestaurant(String idRestaurant){
        if(!billRestApiOutputAdapter.checkRestaurantExistsOutputPort(idRestaurant)){
            throw new IllegalArgumentException("El identificador " + idRestaurant +" no corresponde a un restaurante");
        }
    }
    
    private List<BillDescription> validateDishes(String idRestaurant, List<PayBillDescriptionRequest> dishesRequest, Bill bill) throws BillException{
        List<BillDescription> bills = new ArrayList<>();
        for (PayBillDescriptionRequest dishRequest : dishesRequest) {
            // Revisar que el platillo exista
            DishBillResponse dishBillResponse = this.billRestApiOutputAdapter.getDishInformation(dishRequest.getIdProduct());
            if (dishBillResponse == null) {
                throw new BillException("El platillo con id: " + dishRequest.getIdProduct() + " es inexistente");
            }
            
            // Revisar que el cuarto es del hotel que se agrego
            if (dishBillResponse.getIdRestaurant() == idRestaurant) {
                throw new BillException("El platillo con id: " + dishRequest.getIdProduct() + " no pertenece al mismo restaurante");
            }
            
            // TODO: Revisar si hay una promocion relacionada
            double promotion = 0;
            
            try {
                promotion = this.billRestApiOutputAdapter.findPromotionByProductAndDate(dishRequest.getIdProduct(),bill.getStartDate());
            } catch (Exception e) {
                promotion = 0;
                System.out.println("No hay promocion");
            }
            
            double unitPrice = dishBillResponse.getPrice() - dishBillResponse.getPrice() * promotion/100;
            
            // Agregar las platillos validos
            bills.add(generateBillDescription(dishRequest.getIdProduct(),unitPrice,dishRequest.getQuantity(),bill));
        }
        return bills;
    }
    
    private BillDescription generateBillDescription(String idRoom, double unitPrice,int quantity, Bill bill){
        BillDescription billDescription = BillDescription.builder()
                .idProduct(UUID.fromString(idRoom))
                .unitPrice(unitPrice)
                .quantity(quantity)
                .bill(bill)
                .type("dish")
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

}
