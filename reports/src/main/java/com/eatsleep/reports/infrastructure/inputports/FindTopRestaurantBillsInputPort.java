package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.util.List;

public interface FindTopRestaurantBillsInputPort {
    List<TopsBillDescriptionResponse> findTopRestaurantBills();
}
