package com.eatsleep.promotion.promotion.application.retrievepromotionusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.inputports.RetrievePromotionInputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.db.PromotionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrievePromotionUseCase implements RetrievePromotionInputPort{
    
    private PromotionDbOutputAdapter promotionOutputAdapter;

    @Autowired
    public RetrievePromotionUseCase(PromotionDbOutputAdapter promotionOutputAdapter) {
        this.promotionOutputAdapter = promotionOutputAdapter;
    }

    @Override
    public Optional<Promotion> getPromotionById(String id) {
        // Recuperar el promotion por ID
        Optional<Promotion> promotionEntityOptional = promotionOutputAdapter.getPromotionById(id);

        return promotionEntityOptional;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        // Recuperar todos los promotiones
        return promotionOutputAdapter.getAllPromotions();
    }

}
