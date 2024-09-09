package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi;

import com.eatsleep.bill.common.WebAdapter;
import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.application.findtopspendingclientsusecase.TopSpendingClientResponse;
import com.eatsleep.bill.bill.application.payhotelbillusecase.PayBillHotelRequest;
import com.eatsleep.bill.bill.application.payrestaurantbillusecase.PayBillRestaurantRequest;
import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.BillResponse;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.PayBillHotelResponse;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.PayBillRestaurantResponse;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.TopsBillDescriptionResponse;
import com.eatsleep.bill.bill.infrastructure.inputports.FindToSpendingClientsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetBillsDescriptionByIdClientInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetBillsDescriptionByIdLocationInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopDishBillsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopRestaurantBillsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.GetTopRoomBillsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillHotelInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillRestaurantInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.RetrieveBillsInputPort;
import com.eatsleep.bill.billdescription.domain.BillDescription;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/bills")
@WebAdapter
public class BillControllerAdapter {
    private PayBillHotelInputPort payBillHotelInputPort;
    private PayBillRestaurantInputPort payBillRestaurantInputPort;
    private FindToSpendingClientsInputPort findToSpendingClientsInputPort;
    private GetTopDishBillsInputPort getTopDishBillsInputPort;
    private GetTopRoomBillsInputPort getTopRoomBillsInputPort;
    private GetTopRestaurantBillsInputPort getTopRestaurantBillsInputPort;
    private GetBillsDescriptionByIdLocationInputPort getBillsDescriptionByIdLocationInputPort;
    private GetBillsDescriptionByIdClientInputPort getBillsDescriptionByIdClientInputPort;
    private RetrieveBillsInputPort retrieveBillsInputPort;
    
    @Autowired
    public BillControllerAdapter(PayBillHotelInputPort payBillHotelInputPort, PayBillRestaurantInputPort payBillRestaurantInputPort
        ,FindToSpendingClientsInputPort findToSpendingClientsInputPort, GetTopRoomBillsInputPort getTopRoomBillsInputPort
            ,GetTopDishBillsInputPort getTopDishBillsInputPort
            ,GetTopRestaurantBillsInputPort getTopRestaurantBillsInputPort
            ,GetBillsDescriptionByIdLocationInputPort getBillsDescriptionByIdLocationInputPort
            ,GetBillsDescriptionByIdClientInputPort getBillsDescriptionByIdClientInputPort
            ,RetrieveBillsInputPort retrieveBillsInputPort) {
        this.payBillHotelInputPort = payBillHotelInputPort;
        this.payBillRestaurantInputPort = payBillRestaurantInputPort;
        this.findToSpendingClientsInputPort = findToSpendingClientsInputPort;
        this.getTopRoomBillsInputPort = getTopRoomBillsInputPort;
        this.getTopDishBillsInputPort = getTopDishBillsInputPort;
        this.getTopRestaurantBillsInputPort = getTopRestaurantBillsInputPort;
        this.getBillsDescriptionByIdLocationInputPort = getBillsDescriptionByIdLocationInputPort;
        this.getBillsDescriptionByIdClientInputPort = getBillsDescriptionByIdClientInputPort;
        this.retrieveBillsInputPort = retrieveBillsInputPort;
    }
    
    @PostMapping("/hotel/{idUser}/{idHotel}")
    public ResponseEntity<BillResponse> payBillHotel (
            @PathVariable String idUser, 
            @PathVariable String idHotel, 
            @RequestBody PayBillHotelRequest bill) throws BillException{
        BillResponse createdBill = new BillResponse(payBillHotelInputPort.makeBill(bill,idUser,idHotel));
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }
    
    @PostMapping("/restaurant/{idUser}/{idRestaurant}")
    public ResponseEntity<BillResponse> payBillHotel (
            @PathVariable String idUser, 
            @PathVariable String idRestaurant, 
            @RequestBody PayBillRestaurantRequest bill) throws BillException{
        BillResponse createdBill = new BillResponse(payBillRestaurantInputPort.makeBill(bill,idUser,idRestaurant));
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<String> payBillHotel (){
        return new ResponseEntity<>("Enviado todos los datos", HttpStatus.CREATED);
    }
    
    @GetMapping("/clientsMoreSpending")
    public ResponseEntity<List<TopSpendingClientResponse>> findToSpendingClientsUseCase() {
        return new ResponseEntity<>(this.findToSpendingClientsInputPort.findToSpendingClients(), HttpStatus.OK);
    }
    
    @GetMapping("/topRoom")
    public ResponseEntity<List<TopsBillDescriptionResponse>> findTopRoom() {
        List<BillDescription> billDescriptions = this.getTopRoomBillsInputPort.getTopRoomBills();

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/topDish")
    public ResponseEntity<List<TopsBillDescriptionResponse>> findTopDish() {
        List<BillDescription> billDescriptions = this.getTopDishBillsInputPort.getTopDishBills();

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/topRestaurant")
    public ResponseEntity<List<TopsBillDescriptionResponse>> findTopRestaurant() {
        List<BillDescription> billDescriptions = this.getTopRestaurantBillsInputPort.getTopRestaurantBills();

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/topRoomByLocation/{idLocation}")
    public ResponseEntity<List<TopsBillDescriptionResponse>> findTopRoomBillsByIdLocation(@PathVariable String idLocation) {
        List<BillDescription> billDescriptions = this.getTopRoomBillsInputPort.getTopRoomBillsByIdLocation(idLocation);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/topDishByLocation/{idLocation}")
    public ResponseEntity<List<TopsBillDescriptionResponse>> findTopDishBillsByIdLocation(@PathVariable String idLocation) {
        List<BillDescription> billDescriptions = this.getTopDishBillsInputPort.getTopDishBillsByIdLocation(idLocation);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/rangelocation/{idLocation}")
    public ResponseEntity<List<TopsBillDescriptionResponse>> getBillsDescriptionByIdLocationByDates(
            @PathVariable String idLocation,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BillDescription> billDescriptions = this.getBillsDescriptionByIdLocationInputPort.getBillsDescriptionByIdLocation(idLocation, startDate, endDate);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/rangeclient/{idClient}/{idLocation}")
    public ResponseEntity<List<TopsBillDescriptionResponse>> getBillsDescriptionByClientIdLocationByDates(
            @PathVariable String idClient,
            @PathVariable String idLocation,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BillDescription> billDescriptions = this.getBillsDescriptionByIdClientInputPort.getBillsDescriptionByIdClient(idClient, idLocation, startDate, endDate);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/rangeclient/{idClient}")
    public ResponseEntity<List<TopsBillDescriptionResponse>> getBillsDescriptionByClientByDates(
            @PathVariable String idClient,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BillDescription> billDescriptions = this.getBillsDescriptionByIdClientInputPort.getBillsDescriptionByIdClient(idClient, null, startDate, endDate);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<TopsBillDescriptionResponse> response = billDescriptions.stream()
            .map(TopsBillDescriptionResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/rangeall")
    public ResponseEntity<List<BillResponse>> getAllBillsByDates(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Bill> billDescriptions = this.retrieveBillsInputPort.getAllBillsByDate(startDate,endDate);

        // Convertir la lista de BillDescription a TopsBillDescriptionResponse
        List<BillResponse> response = billDescriptions.stream()
            .map(BillResponse::new)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
