package com.eatsleep.promotion.promotion.application.promotionbyidproductdateusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.inputports.FindPromotionByProductAndDateInputPort;
import com.eatsleep.promotion.promotion.infrastructure.outputadapters.db.PromotionDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@UseCase
@Transactional
public class FindPromotionByProductAndDateUseCase implements FindPromotionByProductAndDateInputPort{
    
    private PromotionDbOutputAdapter promotionOutputAdapter;

    @Autowired
    public  FindPromotionByProductAndDateUseCase(PromotionDbOutputAdapter promotionOutputAdapter) {
        this.promotionOutputAdapter = promotionOutputAdapter;
    }

    @Override
    public Optional<Promotion> findPromotionByProductAndDate(String idProduct, LocalDate date) {
        return this.promotionOutputAdapter.findPromotionByProductAndDate(idProduct, date);
    }
    
    
}
