package com.eatsleep.promotion.comment.infrastructure.inputadapters.restapi.response;

import com.eatsleep.promotion.comment.domain.Comment;
import java.time.LocalDate;
import lombok.Value;

@Value
public class CreateCommentResponse {
    private String idComment;
    private LocalDate dateComment;
    private String description;
    private Double rate;
    private String idProduct;
    private String idUser;

    public CreateCommentResponse(Comment comment) {
        this.idComment = comment.getIdComment().toString();
        this.dateComment = comment.getDateComment();
        this.description = comment.getDescription();
        this.rate = comment.getRate();
        this.idProduct = comment.getIdProduct().toString();
        this.idUser = comment.getUser().toString();
    }
    
    
}
