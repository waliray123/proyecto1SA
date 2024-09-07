package com.eatsleep.promotion.promotion.infrastructure.outputadapters.db;

import com.eatsleep.promotion.common.OutputAdapter;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.outputports.db.CreatePromotionOutputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputports.db.RetrievePromotionOutputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputports.db.UpdatePromotionOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class PromotionDbOutputAdapter implements CreatePromotionOutputPort,UpdatePromotionOutputPort,RetrievePromotionOutputPort{
    
    private PromotionDbEntityRepository promotionDbEntityRepository;

    public PromotionDbOutputAdapter(PromotionDbEntityRepository promotionDbEntityRepository) {
        this.promotionDbEntityRepository = promotionDbEntityRepository;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        PromotionDbEntity promotionDbEntity = PromotionDbEntity.from(promotion);
        promotionDbEntity = promotionDbEntityRepository.save(promotionDbEntity);
        return promotionDbEntity.toDomainModel();
    }
    
    @Override
    public Optional<Promotion> updatePromotion(String id, Promotion updatedPromotion) {

        // Buscar la promoción existente en la base de datos
        Optional<PromotionDbEntity> existingPromotionEntity = promotionDbEntityRepository.findById(id);

        if (existingPromotionEntity.isPresent()) {
            // Actualizar los campos de la entidad existente con los nuevos valores
            PromotionDbEntity promotionEntity = existingPromotionEntity.get();
            promotionEntity.setStartDate(updatedPromotion.getStartDate());
            promotionEntity.setEndDate(updatedPromotion.getEndDate());
            promotionEntity.setType(updatedPromotion.getType());
            promotionEntity.setDescription(updatedPromotion.getDescription());
            promotionEntity.setTypeClient(updatedPromotion.getTypeClient());
            promotionEntity.setIdProduct(updatedPromotion.getIdProduct().toString());

            // Guardar los cambios en la base de datos
            PromotionDbEntity updatedEntity = promotionDbEntityRepository.save(promotionEntity);

            // Convertir la entidad de base de datos actualizada a un modelo de dominio y retornarla
            return Optional.of(updatedEntity.toDomainModel());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Promotion> getPromotionById(String id) {
        Optional<PromotionDbEntity> promotionEntity = promotionDbEntityRepository.findById(id);
        return promotionEntity
                .map(promotionDbEntity -> promotionDbEntity.toDomainModel());
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionDbEntityRepository.findAll().stream()
                .map(promotionDbEntity -> promotionDbEntity.toDomainModel())
                .collect(Collectors.toList());
    }

}
