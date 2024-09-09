package com.eatsleep.reports.infrastructure.inputadapters.restapi;

import com.eatsleep.reports.application.alleemployeeslocationusecase.RetrieveEmployeeResponse;
import com.eatsleep.reports.common.WebAdapter;
import com.eatsleep.reports.infrastructure.inputports.GetAllEmployeesByIdLocationInputPort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/reports")
@WebAdapter
public class ReportsControllerAdapter {
    
    private GetAllEmployeesByIdLocationInputPort getAllEmployeesByIdLocationInputPort;
    
    @Autowired
    public ReportsControllerAdapter(GetAllEmployeesByIdLocationInputPort getAllEmployeesByIdLocationInputPort){
        this.getAllEmployeesByIdLocationInputPort = getAllEmployeesByIdLocationInputPort;
    }
    
    @GetMapping("/all/{idLocation}")
    public ResponseEntity<List<RetrieveEmployeeResponse>> getAllEmployeesByIdLocation(@PathVariable String idLocation) {
        List<RetrieveEmployeeResponse> responseList = this.getAllEmployeesByIdLocationInputPort.getAllEmployeesByIdLocation(idLocation);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    
    

}
