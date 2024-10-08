package com.eatsleep.reports.application.toprestaurantusecase;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.FindTopRestaurantBillsInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class TopRestaurantUseCase implements FindTopRestaurantBillsInputPort{
    
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
