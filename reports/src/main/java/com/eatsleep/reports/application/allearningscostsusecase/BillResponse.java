package com.eatsleep.reports.application.allearningscostsusecase;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class BillResponse {
    private String id;
    private String type;
    private String idLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total;
    private String userId;
    private String reservationId;
    private List<TopsBillDescriptionResponse> descriptions;
}
