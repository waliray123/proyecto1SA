package com.eatsleep.reports.application.allearningscostsusecase;

import java.time.LocalDate;
import lombok.Value;

@Value
public class EarningResponse {
    private String id;
    private String type;
    private String idLocation;
    private LocalDate startDate;
    private double total;

    public EarningResponse(String id, String type, String idLocation, LocalDate startDate, double total) {
        this.id = id;
        this.type = type;
        this.idLocation = idLocation;
        this.startDate = startDate;
        this.total = total;
    }
}
