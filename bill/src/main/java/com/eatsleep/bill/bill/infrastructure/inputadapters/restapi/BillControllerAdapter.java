package com.eatsleep.bill.bill.infrastructure.inputadapters.restapi;

import com.eatsleep.bill.common.WebAdapter;
import com.eatsleep.bill.bill.application.exceptions.BillException;
import com.eatsleep.bill.bill.application.findtopspendingclientsusecase.TopSpendingClientResponse;
import com.eatsleep.bill.bill.application.payhotelbillusecase.PayBillHotelRequest;
import com.eatsleep.bill.bill.application.payrestaurantbillusecase.PayBillRestaurantRequest;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.PayBillHotelResponse;
import com.eatsleep.bill.bill.infrastructure.inputadapters.restapi.response.PayBillRestaurantResponse;
import com.eatsleep.bill.bill.infrastructure.inputports.FindToSpendingClientsInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillHotelInputPort;
import com.eatsleep.bill.bill.infrastructure.inputports.PayBillRestaurantInputPort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/bills")
@WebAdapter
public class BillControllerAdapter {
    private PayBillHotelInputPort payBillHotelInputPort;
    private PayBillRestaurantInputPort payBillRestaurantInputPort;
    private FindToSpendingClientsInputPort findToSpendingClientsInputPort;
    
    @Autowired
    public BillControllerAdapter(PayBillHotelInputPort payBillHotelInputPort, PayBillRestaurantInputPort payBillRestaurantInputPort
        ,FindToSpendingClientsInputPort findToSpendingClientsInputPort) {
        this.payBillHotelInputPort = payBillHotelInputPort;
        this.payBillRestaurantInputPort = payBillRestaurantInputPort;
        this.findToSpendingClientsInputPort = findToSpendingClientsInputPort;
    }
    
    @PostMapping("/hotel/{idUser}/{idHotel}")
    public ResponseEntity<PayBillHotelResponse> payBillHotel (
            @PathVariable String idUser, 
            @PathVariable String idHotel, 
            @RequestBody PayBillHotelRequest bill) throws BillException{
        PayBillHotelResponse createdBill = new PayBillHotelResponse(payBillHotelInputPort.makeBill(bill,idUser,idHotel));
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }
    
    @PostMapping("/restaurant/{idUser}/{idRestaurant}")
    public ResponseEntity<PayBillRestaurantResponse> payBillHotel (
            @PathVariable String idUser, 
            @PathVariable String idRestaurant, 
            @RequestBody PayBillRestaurantRequest bill) throws BillException{
        PayBillRestaurantResponse createdBill = new PayBillRestaurantResponse(payBillRestaurantInputPort.makeBill(bill,idUser,idRestaurant));
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

}
