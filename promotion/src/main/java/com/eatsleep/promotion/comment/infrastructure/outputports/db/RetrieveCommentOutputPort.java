package com.eatsleep.promotion.comment.infrastructure.outputports.db;

import com.eatsleep.promotion.comment.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface RetrieveCommentOutputPort {
    List<Comment> getCommentByIdUser(String idUser);
    List<Comment> getAllComments();
    Optional<Comment> getCommentById(String id);
}
