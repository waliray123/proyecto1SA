package com.eatsleep.reports.infrastructure.outputports.restapi;

import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import java.util.List;

public interface GetAllEmployeesByIdLocationOutputPort {
    List<RetrieveEmployeeResponse> getAllEmployeesByIdLocation(String idLocation);
}
