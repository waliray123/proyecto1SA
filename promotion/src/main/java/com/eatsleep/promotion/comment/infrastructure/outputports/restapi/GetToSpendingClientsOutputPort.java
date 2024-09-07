package com.eatsleep.promotion.comment.infrastructure.outputports.restapi;

import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.TopSpendingClientResponse;
import java.util.List;

public interface GetToSpendingClientsOutputPort {
    List<TopSpendingClientResponse> findToSpendingClients();
}
