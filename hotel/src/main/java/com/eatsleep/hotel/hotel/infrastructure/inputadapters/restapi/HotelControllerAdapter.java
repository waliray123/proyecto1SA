package com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi;

import com.eatsleep.hotel.common.WebAdapter;
import com.eatsleep.hotel.hotel.application.createhotelusecase.CreateHotelRequest;
import com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response.CreateHotelResponse;
import com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response.RetrieveHotelResponse;
import com.eatsleep.hotel.hotel.application.updatehotelusecase.UpdateHotelRequest;
import com.eatsleep.hotel.hotel.infrastructure.inputadapters.restapi.response.UpdateHotelResponse;
import com.eatsleep.hotel.hotel.domain.Hotel;
import com.eatsleep.hotel.hotel.infrastructure.inputports.CreateHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.inputports.ExistHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.inputports.RetrieveHotelInputPort;
import com.eatsleep.hotel.hotel.infrastructure.inputports.UpdateHotelInputPort;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/hotels")
@WebAdapter
public class HotelControllerAdapter {
    private CreateHotelInputPort createHotelInputPort;
    private UpdateHotelInputPort updateHotelInputPort;
    private RetrieveHotelInputPort retrieveHotelInputPort;
    private ExistHotelInputPort existHotelInputPort;

    
    @Autowired
    public HotelControllerAdapter(CreateHotelInputPort createHotelInputPort
            , UpdateHotelInputPort updateHotelInputPort
            , RetrieveHotelInputPort retrieveHotelInputPort
            , ExistHotelInputPort existHotelInputPort) {
        this.createHotelInputPort = createHotelInputPort;
        this.updateHotelInputPort = updateHotelInputPort;
        this.retrieveHotelInputPort = retrieveHotelInputPort;
        this.existHotelInputPort = existHotelInputPort;
    }
    
    @PostMapping("/save")
    public ResponseEntity<CreateHotelResponse> createHotel(@RequestBody CreateHotelRequest hotel) {
        Hotel createdHotel = createHotelInputPort.createHotel(hotel);
        return new ResponseEntity<>(CreateHotelResponse.from(createdHotel), HttpStatus.CREATED);
    }
    
    @GetMapping("/gethotel/{id}")
    public ResponseEntity<RetrieveHotelResponse> getHotelById(@PathVariable String id) {
        Optional<Hotel> hotelOptional = retrieveHotelInputPort.getHotelById(id);
        return hotelOptional.map(hotel -> new ResponseEntity<>(RetrieveHotelResponse.from(hotel), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveHotelResponse>> getAllHotels() {
        List<Hotel> hotels = retrieveHotelInputPort.getAllHotels();
        List<RetrieveHotelResponse> hotelResponses = hotels.stream()
        .map(RetrieveHotelResponse::from)
        .collect(Collectors.toList());
        return new ResponseEntity<>(hotelResponses, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateHotelResponse> updateHotel(@PathVariable String id, @RequestBody UpdateHotelRequest updatedHotelDetails) {
        return updateHotelInputPort.updateHotel(id, updatedHotelDetails)
            .map(hotel -> new ResponseEntity<>(UpdateHotelResponse.from(hotel), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{idHotel}")
    public ResponseEntity<Void> checkHotelExists(@PathVariable String idHotel) {
        if (existHotelInputPort.validateExistHotel(idHotel)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
    
    
}
