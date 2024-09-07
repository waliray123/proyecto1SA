package com.eatsleep.bill.bill.infrastructure.outputports.db;

import com.eatsleep.bill.bill.application.findtopspendingclientsusecase.TopSpendingClientResponse;
import java.util.List;

public interface FindTopSpendingClientsOutputPort {
    List<TopSpendingClientResponse> findTopSpendingClients();
}
