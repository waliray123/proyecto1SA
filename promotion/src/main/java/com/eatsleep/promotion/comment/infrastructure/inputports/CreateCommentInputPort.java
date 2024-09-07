package com.eatsleep.promotion.comment.infrastructure.inputports;

import com.eatsleep.promotion.comment.application.createcommentusecase.CreateCommentRequest;
import com.eatsleep.promotion.comment.domain.Comment;

public interface CreateCommentInputPort {
    
    Comment createComment(CreateCommentRequest comment, String type);

}
