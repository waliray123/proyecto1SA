package com.eatsleep.promotion.comment.application.updatecommentusecase;

import java.time.LocalDate;
import lombok.Value;

@Value
public class UpdateCommentRequest {    
    private LocalDate dateComment;
    private String description;
    private Double rate;
    private String idProduct;
    private String idUser;
}
