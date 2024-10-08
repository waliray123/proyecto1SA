package com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi;

import com.eatsleep.promotion.common.WebAdapter;
import com.eatsleep.promotion.promotion.application.createpromotionusecase.CreatePromotionRequest;
import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.CreatePromotionResponse;
import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.RetrievePromotionResponse;
import com.eatsleep.promotion.promotion.application.updatepromotionusecase.UpdatePromotionRequest;
import com.eatsleep.promotion.promotion.domain.Promotion;
import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.RelatedPromotionToCreateResponse;
import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.UpdatePromotionResponse;
import com.eatsleep.promotion.promotion.infrastructure.inputports.CreatePromotionInputPort;
import com.eatsleep.promotion.promotion.infrastructure.inputports.FindPromotionByProductAndDateInputPort;
import com.eatsleep.promotion.promotion.infrastructure.inputports.GetRelatedPromotionsToCreateInputPort;
import com.eatsleep.promotion.promotion.infrastructure.inputports.RetrievePromotionInputPort;
import com.eatsleep.promotion.promotion.infrastructure.inputports.UpdatePromotionInputPort;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/promotions")
@WebAdapter
public class PromotionControllerAdapter {
    private CreatePromotionInputPort createPromotionInputPort;
    private UpdatePromotionInputPort updatePromotionInputPort;
    private RetrievePromotionInputPort retrievePromotionInputPort;
    private GetRelatedPromotionsToCreateInputPort getRelatedPromotionsToCreateInputPort;
    private FindPromotionByProductAndDateInputPort findPromotionByProductAndDateInputPort;

    
    @Autowired
    public PromotionControllerAdapter(CreatePromotionInputPort createPromotionInputPort
            , UpdatePromotionInputPort updatePromotionInputPort
            , RetrievePromotionInputPort retrievePromotionInputPort
            , GetRelatedPromotionsToCreateInputPort getRelatedPromotionsToCreateInputPort
            , FindPromotionByProductAndDateInputPort findPromotionByProductAndDateInputPort) {
        this.createPromotionInputPort = createPromotionInputPort;
        this.updatePromotionInputPort = updatePromotionInputPort;
        this.retrievePromotionInputPort = retrievePromotionInputPort;
        this.getRelatedPromotionsToCreateInputPort = getRelatedPromotionsToCreateInputPort;
        this.findPromotionByProductAndDateInputPort = findPromotionByProductAndDateInputPort;
    }
    
    @PostMapping("/save")
    public ResponseEntity<CreatePromotionResponse> createPromotion(@RequestBody CreatePromotionRequest promotion) {
        Promotion createdPromotion = createPromotionInputPort.createPromotion(promotion);
        return new ResponseEntity<>(new CreatePromotionResponse(createdPromotion), HttpStatus.CREATED);
    }
    
    @GetMapping("/getpromotion/{id}")
    public ResponseEntity<RetrievePromotionResponse> getPromotionById(@PathVariable String id) {
        Optional<Promotion> promotionOptional = retrievePromotionInputPort.getPromotionById(id);
        return promotionOptional.map(promotion -> new ResponseEntity<>(new RetrievePromotionResponse(promotion), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrievePromotionResponse>> getAllPromotions() {
        List<Promotion> promotions = retrievePromotionInputPort.getAllPromotions();
        List<RetrievePromotionResponse> responseList = promotions.stream()
                .map(RetrievePromotionResponse::new)
                .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdatePromotionResponse> updatePromotion(@PathVariable String id, @RequestBody UpdatePromotionRequest updatedPromotionDetails) {
        return updatePromotionInputPort.updatePromotion(id, updatedPromotionDetails)
                .map(promotion -> new ResponseEntity<>(new UpdatePromotionResponse(promotion), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/related")
    public ResponseEntity<List<RelatedPromotionToCreateResponse>> getRelatedPromotions() {
        List<RelatedPromotionToCreateResponse> relatedPromotions = this.getRelatedPromotionsToCreateInputPort.getRelatedPromotionsToCreateInputPort();
        return new ResponseEntity<>(relatedPromotions, HttpStatus.OK);
    }
    
    @GetMapping("/product/{idProduct}/date/{date}")
    public ResponseEntity<Double> getPromotionByProductAndDate(
            @PathVariable String idProduct,
            @PathVariable String date) {

        LocalDate localDate = LocalDate.parse(date);  // Convertir la fecha de String a LocalDate
        Optional<Promotion> promotion = this.findPromotionByProductAndDateInputPort.findPromotionByProductAndDate(idProduct, localDate);

        // Si la promoción está presente, devuelve el valor de la promoción, sino devuelve NOT_FOUND
        return promotion.map(p -> new ResponseEntity<>(p.getValuePromotion(), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
