package com.eatsleep.reports.infrastructure.inputports;

import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import java.util.List;

public interface GetAllEmployeesByIdLocationInputPort {
    List<RetrieveEmployeeResponse> getAllEmployeesByIdLocation(String idLocation);
}
