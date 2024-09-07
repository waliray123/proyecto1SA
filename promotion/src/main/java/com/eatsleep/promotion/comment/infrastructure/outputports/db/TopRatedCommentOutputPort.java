package com.eatsleep.promotion.comment.infrastructure.outputports.db;

import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.TopRatedCommentResponse;
import java.util.List;

public interface TopRatedCommentOutputPort {
    List<TopRatedCommentResponse> getTopRatedRoomComments();
    List<TopRatedCommentResponse> getTopRatedDishComments();
}
