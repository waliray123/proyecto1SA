package com.eatsleep.reports.application.toproomusecase;

import com.eatsleep.reports.application.allearningslocationdateusecase.TopsBillDescriptionResponse;
import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.FindTopRoomInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class TopRoomUseCase implements FindTopRoomInputPort {
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public TopRoomUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<TopsBillDescriptionResponse> findTopRoom() {
        return this.reportsRestApiOutputAdapter.findTopRoom();
    }

    @Override
    public List<TopsBillDescriptionResponse> findTopRoomBillsByIdLocation(String idLocation) {
        return this.reportsRestApiOutputAdapter.findTopRoomBillsByIdLocation(idLocation);
    }


    
    
}
