package com.eatsleep.promotion.comment.application.createcommentusecase;

import java.time.LocalDate;
import lombok.Value;


@Value
public class CreateCommentRequest {    
    private LocalDate dateComment;
    private String description;
    private Double rate;
    private String idProduct;
    private String idUser;
}
