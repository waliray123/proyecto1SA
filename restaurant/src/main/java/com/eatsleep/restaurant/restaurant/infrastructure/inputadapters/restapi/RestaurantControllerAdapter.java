package com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi;

import com.eatsleep.restaurant.common.WebAdapter;
import com.eatsleep.restaurant.restaurant.application.createrestaurantusecase.CreateRestaurantRequest;
import com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response.CreateRestaurantResponse;
import com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response.RetrieveRestaurantResponse;
import com.eatsleep.restaurant.restaurant.application.updaterestaurantusecase.UpdateRestaurantRequest;
import com.eatsleep.restaurant.restaurant.domain.Restaurant;
import com.eatsleep.restaurant.restaurant.infrastructure.inputadapters.restapi.response.UpdateRestaurantResponse;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.CreateRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.ExistRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.RetrieveRestaurantInputPort;
import com.eatsleep.restaurant.restaurant.infrastructure.inputports.UpdateRestaurantInputPort;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/v1/restaurants")
@WebAdapter
public class RestaurantControllerAdapter {
    private CreateRestaurantInputPort createRestaurantInputPort;
    private UpdateRestaurantInputPort updateRestaurantInputPort;
    private RetrieveRestaurantInputPort retrieveRestaurantInputPort;
    private ExistRestaurantInputPort existRestaurantInputPort;

    
    @Autowired
    public RestaurantControllerAdapter(CreateRestaurantInputPort createRestaurantInputPort
            , UpdateRestaurantInputPort updateRestaurantInputPort
            , RetrieveRestaurantInputPort retrieveRestaurantInputPort
            , ExistRestaurantInputPort existRestaurantInputPort) {
        this.createRestaurantInputPort = createRestaurantInputPort;
        this.updateRestaurantInputPort = updateRestaurantInputPort;
        this.retrieveRestaurantInputPort = retrieveRestaurantInputPort;
        this.existRestaurantInputPort = existRestaurantInputPort;
    }
    
    @PostMapping("/save")
    public ResponseEntity<CreateRestaurantResponse> createRestaurant(@RequestBody CreateRestaurantRequest restaurant) {
        Restaurant createdRestaurant = createRestaurantInputPort.createRestaurant(restaurant);
        return new ResponseEntity<>(new CreateRestaurantResponse(createdRestaurant), HttpStatus.CREATED);
    }
    
    @GetMapping("/getrestaurant/{id}")
    public ResponseEntity<RetrieveRestaurantResponse> getRestaurantById(@PathVariable String id) {
        Optional<Restaurant> restaurantOptional = retrieveRestaurantInputPort.getRestaurantById(id);
        return restaurantOptional.map(restaurant -> new ResponseEntity<>(new RetrieveRestaurantResponse(restaurant), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveRestaurantResponse>> getAllRestaurants() {
        List<Restaurant> restaurants = retrieveRestaurantInputPort.getAllRestaurants();
        List<RetrieveRestaurantResponse> responseList = restaurants.stream()
                .map(RetrieveRestaurantResponse::new)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateRestaurantResponse> updateRestaurant(@PathVariable String id, @RequestBody UpdateRestaurantRequest updatedRestaurantDetails) {
        return updateRestaurantInputPort.updateRestaurant(id, updatedRestaurantDetails)
                .map(restaurant -> new ResponseEntity<>(new UpdateRestaurantResponse(restaurant), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{idRestaurant}")
    public ResponseEntity<Void> checkRestaurantExists(@PathVariable String idRestaurant) {
        if (existRestaurantInputPort.checkExistRestaurant(idRestaurant)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
