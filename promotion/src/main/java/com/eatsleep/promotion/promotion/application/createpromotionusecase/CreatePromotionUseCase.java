package com.eatsleep.promotion.promotion.application.createpromotionusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.inputports.CreatePromotionInputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.db.PromotionDbOutputAdapter;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.restapi.PromotionRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreatePromotionUseCase implements CreatePromotionInputPort{
    
    private PromotionDbOutputAdapter promotionOutputAdapter;
    private PromotionRestApiOutputAdapter promotionRestApiOutputAdapter;

    @Autowired
    public CreatePromotionUseCase(PromotionDbOutputAdapter promotionOutputAdapter,PromotionRestApiOutputAdapter promotionRestApiOutputAdapter) {
        this.promotionOutputAdapter = promotionOutputAdapter;
        this.promotionRestApiOutputAdapter = promotionRestApiOutputAdapter;
    }

    @Override
    public Promotion createPromotion(CreatePromotionRequest promotionRequest) {        
        // Validar la información del promotion
        validatePromotionRequest(promotionRequest);
        
        validatePromotionProduct(promotionRequest);
        
        
        // Crear el promotion
        Promotion promotion = Promotion.builder()
                .startDate(promotionRequest.getStartDate())
                .endDate(promotionRequest.getEndDate())
                .type(promotionRequest.getType())
                .description(promotionRequest.getDescription())
                .typeClient(promotionRequest.getTypeClient())
                .idProduct(UUID.fromString(promotionRequest.getIdProduct()))
                .valuePromotion(promotionRequest.getValuePromotion())
                .build();
            
        
        // Persistir el promotion en la base de datos usando el Output Adapter
        Promotion savedPromotion = promotionOutputAdapter.createPromotion(promotion);

        return savedPromotion;
    }
    
    private void validatePromotionRequest(CreatePromotionRequest promotionRequest){
        if (promotionRequest.getStartDate() == null) {
            throw new IllegalArgumentException("La fecha inicial es obligatoria para generar la promocion");
        }
        if (promotionRequest.getEndDate() == null) {
            throw new IllegalArgumentException("La fecha final es obligatoria para generar la promocion");
        }
        if (promotionRequest.getType() == null || promotionRequest.getType().isEmpty()) {
            throw new IllegalArgumentException("El tipo de la promocion debe ser obligatoria");
        }
        if (promotionRequest.getDescription() == null || promotionRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripcion de la promocion debe ser obligatoria");
        }
        if (promotionRequest.getIdProduct() == null || promotionRequest.getIdProduct().isEmpty()) {
            throw new IllegalArgumentException("El producto a aplicar la promocion es obligatoria");
        }
        if (promotionRequest.getValuePromotion() > 0) {
            throw new IllegalArgumentException("El valor de la promocion debe ser mayor a cero");
        }
    }
    
    private void validatePromotionProduct(CreatePromotionRequest promotionRequest){
        if(promotionRequest.getType().contains("hotel")){
            //Validar el hotel
            if (!promotionRestApiOutputAdapter.checkHotelExistsOutputPort(promotionRequest.getIdProduct())) {
                throw new IllegalArgumentException("El id:" + promotionRequest.getIdProduct() + " del hotel no es valido");
            }
        }else if(promotionRequest.getType().contains("restaurant")){
            //Validar el restaurante
            if (!promotionRestApiOutputAdapter.checkRestaurantExistsOutputPort(promotionRequest.getIdProduct())) {
                throw new IllegalArgumentException("El id:" + promotionRequest.getIdProduct() + " del restaurant no es valido");
            }
        }else if(promotionRequest.getType().contains("dish")){
            //Validar el platillo
            if (!promotionRestApiOutputAdapter.checkDishExistsOutputPort(promotionRequest.getIdProduct())) {
                throw new IllegalArgumentException("El id:" + promotionRequest.getIdProduct() + " del platillo no es valido");
            }
        }else if(promotionRequest.getType().contains("room")){
            //Validar el cuarto
            if (!promotionRestApiOutputAdapter.checkRoomExistsOutputPort(promotionRequest.getIdProduct())) {
                throw new IllegalArgumentException("El id:" + promotionRequest.getIdProduct() + " del cuarto no es valido");
            }
        }else{
            throw new IllegalArgumentException("La promocion debe contener un tipo correcto: hotel, restaurant, dish o room");
        }
    }

}
