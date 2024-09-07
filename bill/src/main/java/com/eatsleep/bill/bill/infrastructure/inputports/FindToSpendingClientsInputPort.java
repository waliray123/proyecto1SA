package com.eatsleep.bill.bill.infrastructure.inputports;

import com.eatsleep.bill.bill.application.findtopspendingclientsusecase.TopSpendingClientResponse;
import java.util.List;

public interface FindToSpendingClientsInputPort {
    List<TopSpendingClientResponse> findToSpendingClients();
}
