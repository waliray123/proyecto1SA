package com.eatsleep.promotion.promotion.application.updatepromotionusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.inputports.UpdatePromotionInputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.db.PromotionDbOutputAdapter;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.restapi.PromotionRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class UpdatePromotionUseCase implements UpdatePromotionInputPort{
    
    private PromotionDbOutputAdapter promotionOutputAdapter;
    private PromotionRestApiOutputAdapter promotionRestApiOutputAdapter;

    @Autowired
    public UpdatePromotionUseCase(PromotionDbOutputAdapter promotionOutputAdapter,PromotionRestApiOutputAdapter promotionRestApiOutputAdapter) {
        this.promotionOutputAdapter = promotionOutputAdapter;
        this.promotionRestApiOutputAdapter = promotionRestApiOutputAdapter;
    }

    @Override
    public Optional<Promotion> updatePromotion(String idPromotion, UpdatePromotionRequest promotionRequest) {
        validatePromotionRequest(promotionRequest);
        validatePromotionProduct(promotionRequest);
        
        // Buscar la promoción existente en la base de datos
        Optional<Promotion> existingPromotionOpt = promotionOutputAdapter.getPromotionById(idPromotion);

        // Si la promoción no existe, retornar un Optional vacío
        if (!existingPromotionOpt.isPresent()) {
            return Optional.empty();
        }

        // Obtener la promoción existente
        Promotion existingPromotion = existingPromotionOpt.get();

        // Actualizar los campos de la promoción con los valores del request
        existingPromotion.setStartDate(promotionRequest.getStartDate());
        existingPromotion.setEndDate(promotionRequest.getEndDate());
        existingPromotion.setType(promotionRequest.getType());
        existingPromotion.setDescription(promotionRequest.getDescription());
        existingPromotion.setTypeClient(promotionRequest.getTypeClient());
        existingPromotion.setIdProduct(UUID.fromString(promotionRequest.getIdProduct()));
        existingPromotion.setValuePromotion(promotionRequest.getValuePromotion());

        // Guardar la promoción actualizada en la base de datos
        Promotion updatedPromotion = promotionOutputAdapter.createPromotion(existingPromotion);

        // Retornar la promoción actualizada dentro de un Optional
        return Optional.of(updatedPromotion);
    }

    
    private void validatePromotionRequest(UpdatePromotionRequest promotionRequest){
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
        if (promotionRequest.getValuePromotion() < 0) {
            throw new IllegalArgumentException("El valor de la promocion debe ser mayor a cero");
        }
    }
    
    private void validatePromotionProduct(UpdatePromotionRequest promotionRequest){
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
