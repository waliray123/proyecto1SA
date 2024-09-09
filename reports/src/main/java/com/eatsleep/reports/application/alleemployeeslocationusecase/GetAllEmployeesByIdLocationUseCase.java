package com.eatsleep.reports.application.alleemployeeslocationusecase;

import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.GetAllEmployeesByIdLocationInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetAllEmployeesByIdLocationUseCase implements GetAllEmployeesByIdLocationInputPort{
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public GetAllEmployeesByIdLocationUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<RetrieveEmployeeResponse> getAllEmployeesByIdLocation(String idLocation) {
        return this.reportsRestApiOutputAdapter.getAllEmployeesByIdLocation(idLocation);
    }
    
}
