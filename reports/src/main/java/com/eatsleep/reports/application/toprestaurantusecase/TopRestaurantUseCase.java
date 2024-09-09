package com.eatsleep.reports.application.toprestaurantusecase;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import com.eatsleep.reports.infrastructure.outputports.restapi.FindTopRestaurantBillsOutputPort;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class TopRestaurantUseCase implements FindTopRestaurantBillsOutputPort{
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public TopRestaurantUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<TopsBillDescriptionResponse> findTopRestaurantBills() {
        return this.reportsRestApiOutputAdapter.findTopRestaurantBills();
    }

    
    
}
