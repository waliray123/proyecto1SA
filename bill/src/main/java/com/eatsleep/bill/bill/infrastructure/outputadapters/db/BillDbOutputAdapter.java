package com.eatsleep.bill.bill.infrastructure.outputadapters.db;

import com.eatsleep.bill.bill.application.findtopspendingclientsusecase.TopSpendingClientResponse;
import com.eatsleep.bill.common.OutputAdapter;
import com.eatsleep.bill.bill.domain.Bill;
import com.eatsleep.bill.bill.infrastructure.outputports.db.FindTopSpendingClientsOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.db.PayHotelBillOutputPort;
import com.eatsleep.bill.bill.infrastructure.outputports.db.PayRestaurantBillOutputPort;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class BillDbOutputAdapter implements PayHotelBillOutputPort,PayRestaurantBillOutputPort,FindTopSpendingClientsOutputPort{
    
    private BillDbEntityRepository billDbEntityRepository;

    @Autowired
    public BillDbOutputAdapter(BillDbEntityRepository billDbEntityRepository) {
        this.billDbEntityRepository = billDbEntityRepository;
    }
    
    @Override
    public Bill payHotelBill(Bill bill) {
        BillDbEntity billEntity = BillDbEntity.fromDomainModel(bill);
        return billDbEntityRepository.save(billEntity).toDomainModel();
    }

    @Override
    public Bill payRestaurantBill(Bill bill) {
        BillDbEntity billEntity = BillDbEntity.fromDomainModel(bill);
        return billDbEntityRepository.save(billEntity).toDomainModel();
    }
    
    public List<TopSpendingClientResponse> findTopSpendingClients() {
        // Obtener los resultados del repositorio
        List<Object[]> result = billDbEntityRepository.findTopSpendingClients();

        // Mapear los resultados a objetos TopSpendingClientResponse
        return result.stream()
            .map(obj -> new TopSpendingClientResponse(
                (String) obj[0],    // userId
                (Double) obj[1]))   // totalSpent
            .collect(Collectors.toList());
    }

}
