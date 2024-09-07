package com.eatsleep.promotion.comment.infrastructure.outputports.db;

import com.eatsleep.promotion.comment.domain.Comment;
import java.util.Optional;

public interface UpdateCommentOutputPort {
    Optional<Comment> updateComment(String id, Comment updatedComment);
}
