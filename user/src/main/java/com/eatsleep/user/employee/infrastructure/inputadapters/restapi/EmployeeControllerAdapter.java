package com.eatsleep.user.employee.infrastructure.inputadapters.restapi;

import com.eatsleep.user.common.WebAdapter;
import com.eatsleep.user.employee.application.createemployeeusecase.CreateEmployeeRequest;
import com.eatsleep.user.employee.application.exceptions.EmployeeAlreadyExistsException;
import com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response.CreateEmployeeResponse;
import com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response.RetrieveEmployeeResponse;
import com.eatsleep.user.employee.application.updateemployeeusecase.UpdateEmployeeRequest;
import com.eatsleep.user.employee.domain.Employee;
import com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response.PaymentResponse;
import com.eatsleep.user.employee.infrastructure.inputadapters.restapi.response.UpdateEmployeeResponse;
import com.eatsleep.user.employee.infrastructure.inputports.CreateEmployeeInputPort;
import com.eatsleep.user.employee.infrastructure.inputports.FindEmployeesByIdLocationInputPort;
import com.eatsleep.user.employee.infrastructure.inputports.GetAllPaymentsInputPort;
import com.eatsleep.user.employee.infrastructure.inputports.RetrieveEmployeeInputPort;
import com.eatsleep.user.employee.infrastructure.inputports.UpdateEmployeeInputPort;
import com.eatsleep.user.payment.domain.Payment;
import com.eatsleep.user.payment.infrastructure.inputports.PayAllEmployeesInputPort;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/employees")
@WebAdapter
public class EmployeeControllerAdapter {
    private CreateEmployeeInputPort createEmployeeInputPort;
    private UpdateEmployeeInputPort updateEmployeeInputPort;
    private RetrieveEmployeeInputPort retrieveEmployeeInputPort;
    private PayAllEmployeesInputPort payAllEmployeesInputPort;
    private FindEmployeesByIdLocationInputPort findEmployeesByIdLocationInputPort;
    private GetAllPaymentsInputPort getAllPaymentsInputPort; 

    
    @Autowired
    public EmployeeControllerAdapter(CreateEmployeeInputPort createEmployeeInputPort
            , UpdateEmployeeInputPort updateEmployeeInputPort
            , RetrieveEmployeeInputPort retrieveEmployeeInputPort
            , PayAllEmployeesInputPort payAllEmployeesInputPort
            , FindEmployeesByIdLocationInputPort findEmployeesByIdLocationInputPort
            , GetAllPaymentsInputPort getAllPaymentsInputPort) {
        this.createEmployeeInputPort = createEmployeeInputPort;
        this.updateEmployeeInputPort = updateEmployeeInputPort;
        this.retrieveEmployeeInputPort = retrieveEmployeeInputPort;
        this.payAllEmployeesInputPort = payAllEmployeesInputPort;
        this.findEmployeesByIdLocationInputPort = findEmployeesByIdLocationInputPort;
        this.getAllPaymentsInputPort = getAllPaymentsInputPort;
    }
    
    @PostMapping("/savehotel/{idHotel}")
    public ResponseEntity<CreateEmployeeResponse> createEmployeeHotel(@PathVariable String idHotel, @RequestBody CreateEmployeeRequest employee) throws EmployeeAlreadyExistsException{
        Employee createdEmployee = createEmployeeInputPort.createEmployee(employee,"hotel",idHotel);
        return new ResponseEntity<>(new CreateEmployeeResponse(createdEmployee), HttpStatus.CREATED);
    }
    
    @PostMapping("/saverestaurant/{idRestaurant}")
    public ResponseEntity<CreateEmployeeResponse> createEmployeeRestaurant(@PathVariable String idRestaurant, @RequestBody CreateEmployeeRequest employee) throws EmployeeAlreadyExistsException{
        Employee createdEmployee = createEmployeeInputPort.createEmployee(employee,"restaurant",idRestaurant);
        return new ResponseEntity<>(new CreateEmployeeResponse(createdEmployee), HttpStatus.CREATED);
    }
    
    @GetMapping("/getemployee/{id}")
    public ResponseEntity<RetrieveEmployeeResponse> getEmployeeById(@PathVariable String id) {
        Optional<Employee> employeeOptional = retrieveEmployeeInputPort.getEmployeeById(id);
        return employeeOptional.map(employee -> new ResponseEntity<>(new RetrieveEmployeeResponse(employee), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveEmployeeResponse>> getAllEmployees() {
        List<Employee> employees = retrieveEmployeeInputPort.getAllEmployees();
        List<RetrieveEmployeeResponse> responseList = employees.stream()
                .map(RetrieveEmployeeResponse::new)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @PutMapping("/updatehotel/{idHotel}/{id}")
    public ResponseEntity<UpdateEmployeeResponse> updateEmployeeHotel(@PathVariable String idHotel, @PathVariable String id, @RequestBody UpdateEmployeeRequest updatedEmployeeDetails) {
        return updateEmployeeInputPort.updateEmployee(id, updatedEmployeeDetails, "hotel", idHotel)
                .map(employee -> new ResponseEntity<>(new UpdateEmployeeResponse(employee), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/updaterestaurant/{idRestaurant}/{id}")
    public ResponseEntity<UpdateEmployeeResponse> updateEmployeeRestaurant(@PathVariable String idRestaurant, @PathVariable String id, @RequestBody UpdateEmployeeRequest updatedEmployeeDetails) {
        return updateEmployeeInputPort.updateEmployee(id, updatedEmployeeDetails, "restaurant", idRestaurant)
                .map(employee -> new ResponseEntity<>(new UpdateEmployeeResponse(employee), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @PostMapping("/pay")
    public ResponseEntity<List<PaymentResponse>> payAllEmployees(){
        List<Payment> payments = this.payAllEmployeesInputPort.payAllEmployees();
        List<PaymentResponse> response = payments.stream()
                .map(PaymentResponse::new) // Utilize the constructor for mapping
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/all/{idLocation}")
    public ResponseEntity<List<RetrieveEmployeeResponse>> getAllEmployeesByIdLocation(@PathVariable String idLocation) {
        List<Employee> employees = this.findEmployeesByIdLocationInputPort.findEmployeesByIdLocationInputPort(idLocation);
        List<RetrieveEmployeeResponse> responseList = employees.stream()
                .map(RetrieveEmployeeResponse::new)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @GetMapping("/rangepayments")
    public ResponseEntity<List<PaymentResponse>> getPaymentsBetweenDates(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        List<Payment> payments = this.getAllPaymentsInputPort.getAllPayments(startDate, endDate);
        List<PaymentResponse> response = payments.stream()
                .map(PaymentResponse::new) // Utilize the constructor for mapping
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
}
