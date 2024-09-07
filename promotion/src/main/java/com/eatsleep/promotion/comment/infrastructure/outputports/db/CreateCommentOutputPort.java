package com.eatsleep.promotion.comment.infrastructure.outputports.db;

import com.eatsleep.promotion.comment.domain.Comment;

public interface CreateCommentOutputPort {
    Comment createComment(Comment comment);
}
