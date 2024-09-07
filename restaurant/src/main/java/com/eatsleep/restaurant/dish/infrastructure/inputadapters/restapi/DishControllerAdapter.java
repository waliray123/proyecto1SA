package com.eatsleep.restaurant.dish.infrastructure.inputadapters.restapi;

import com.eatsleep.restaurant.common.WebAdapter;
import com.eatsleep.restaurant.dish.application.createdishusecase.CreateDishRequest;
import com.eatsleep.restaurant.dish.infrastructure.inputadapters.restapi.response.CreateDishResponse;
import com.eatsleep.restaurant.dish.infrastructure.inputadapters.restapi.response.RetrieveDishResponse;
import com.eatsleep.restaurant.dish.application.updatedishusecase.UpdateDishRequest;
import com.eatsleep.restaurant.dish.domain.Dish;
import com.eatsleep.restaurant.dish.infrastructure.inputadapters.restapi.response.UpdateDishResponse;
import com.eatsleep.restaurant.dish.infrastructure.inputports.CreateDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.inputports.ExistDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.inputports.RetrieveDishInputPort;
import com.eatsleep.restaurant.dish.infrastructure.inputports.UpdateDishInputPort;
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
@RequestMapping("/v1/dishes")
@WebAdapter
public class DishControllerAdapter {

    private CreateDishInputPort createDishInputPort;
    private UpdateDishInputPort updateDishInputPort;
    private RetrieveDishInputPort retrieveDishInputPort;
    private ExistDishInputPort existDishInputPort;

    @Autowired
    public DishControllerAdapter(CreateDishInputPort createDishInputPort
            , UpdateDishInputPort updateDishInputPort
            , RetrieveDishInputPort retrieveDishInputPort
            ,ExistDishInputPort existDishInputPort) {
        this.createDishInputPort = createDishInputPort;
        this.updateDishInputPort = updateDishInputPort;
        this.retrieveDishInputPort = retrieveDishInputPort;
        this.existDishInputPort = existDishInputPort;
    }
    
    
    @PostMapping("/save")
    public ResponseEntity<CreateDishResponse> createDish(@RequestBody CreateDishRequest dish) {
        Dish createdDish = createDishInputPort.createDish(dish);
        
        return new ResponseEntity<>(new CreateDishResponse(createdDish), HttpStatus.CREATED);
    }
    
    @GetMapping("/getdish/{id}")
    public ResponseEntity<RetrieveDishResponse> getDishById(@PathVariable String id) {
        Optional<Dish> dishOptional = retrieveDishInputPort.getDishById(id);
        return dishOptional.map(dish -> new ResponseEntity<>(new RetrieveDishResponse(dish), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveDishResponse>> getAllDishs() {
        List<Dish> dishes = retrieveDishInputPort.getAllDishs();
        List<RetrieveDishResponse> dishesReponse = dishes.stream()
                .map(RetrieveDishResponse::new)
                .toList();
        return new ResponseEntity<>(dishesReponse, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateDishResponse> updateDish(@PathVariable String id, @RequestBody UpdateDishRequest updatedDishDetails) {
        return updateDishInputPort.updateDish(id, updatedDishDetails)
                .map(dish -> new ResponseEntity<>(new UpdateDishResponse(dish), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{idDish}")
    public ResponseEntity<Void> checkDishExists(@PathVariable String idDish) {
        if (existDishInputPort.checkExistDish(idDish)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
