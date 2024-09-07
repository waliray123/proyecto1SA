package com.eatsleep.bill.bill.application.findtopspendingclientsusecase;

import com.eatsleep.bill.bill.infrastructure.inputports.FindToSpendingClientsInputPort;
import com.eatsleep.bill.common.UseCase;
import com.eatsleep.bill.bill.infrastructure.outputadapters.db.BillDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class FindToSpendingClientsUseCase implements FindToSpendingClientsInputPort{
    
    private BillDbOutputAdapter billDbOutputAdapter;

    @Autowired
    public FindToSpendingClientsUseCase(BillDbOutputAdapter billDbOutputAdapter) {
        this.billDbOutputAdapter = billDbOutputAdapter;
    }

    @Override
    public List<TopSpendingClientResponse> findToSpendingClients() {
        List<TopSpendingClientResponse> topClients = this.billDbOutputAdapter.findTopSpendingClients();
        List<TopSpendingClientResponse> top5Clients = topClients.size() > 5 ? topClients.subList(0, 5) : topClients;
        return top5Clients;
    }
    
    


}
