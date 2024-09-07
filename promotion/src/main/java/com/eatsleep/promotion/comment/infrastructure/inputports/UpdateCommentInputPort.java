package com.eatsleep.promotion.comment.infrastructure.inputports;

import com.eatsleep.promotion.comment.application.updatecommentusecase.UpdateCommentRequest;
import com.eatsleep.promotion.comment.domain.Comment;
import java.util.Optional;

public interface UpdateCommentInputPort {
    Optional<Comment> updateComment(String idComment, UpdateCommentRequest comment, String type);
}
