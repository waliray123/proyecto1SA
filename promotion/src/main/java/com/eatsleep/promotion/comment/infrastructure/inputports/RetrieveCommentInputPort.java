package com.eatsleep.promotion.comment.infrastructure.inputports;

import com.eatsleep.promotion.comment.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface RetrieveCommentInputPort {
    List<Comment> getCommentsByIdUser(String idUser);
    Optional<Comment> getCommentById(String id);
    List<Comment> getAllComments();
}
