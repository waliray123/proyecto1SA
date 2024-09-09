package com.eatsleep.reports.application.allbillsclientusecase;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.application.alleemployeeslocationusecase.*;
import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByClientByDatesInputPort;
import com.eatsleep.reports.infrastructure.inputports.GetBillsDescriptionByClientIdLocationByDatesInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class AllBillsClientUseCase implements GetBillsDescriptionByClientByDatesInputPort,GetBillsDescriptionByClientIdLocationByDatesInputPort{
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public AllBillsClientUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByClientByDates(String idClient, LocalDate startDate, LocalDate endDate) {
        return this.reportsRestApiOutputAdapter.getBillsDescriptionByClientByDates(idClient, startDate, endDate);
    }

    @Override
    public List<TopsBillDescriptionResponse> getBillsDescriptionByClientIdLocationByDates(String idClient, String idLocation, LocalDate startDate, LocalDate endDate) {
        return this.reportsRestApiOutputAdapter.getBillsDescriptionByClientIdLocationByDates(idClient,idLocation, startDate, endDate);
    }
    
}
