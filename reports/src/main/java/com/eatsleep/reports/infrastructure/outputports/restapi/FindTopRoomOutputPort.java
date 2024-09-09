package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.util.List;

public interface FindTopRoomOutputPort {
    List<TopsBillDescriptionResponse> findTopRoom();
    List<TopsBillDescriptionResponse> findTopRoomBillsByIdLocation(String idLocation);
}
