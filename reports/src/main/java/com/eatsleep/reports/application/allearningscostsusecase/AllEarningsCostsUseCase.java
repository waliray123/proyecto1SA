package com.eatsleep.reports.application.allearningscostsusecase;

import com.eatsleep.reports.common.UseCase;
import com.eatsleep.reports.infrastructure.inputports.GetEarningsCostsInputPort;
import com.eatsleep.reports.infrastructure.outputadapters.restapi.ReportsRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class AllEarningsCostsUseCase implements GetEarningsCostsInputPort{
    
    private ReportsRestApiOutputAdapter reportsRestApiOutputAdapter;

    @Autowired
    public AllEarningsCostsUseCase(ReportsRestApiOutputAdapter reportsRestApiOutputAdapter) {
        this.reportsRestApiOutputAdapter = reportsRestApiOutputAdapter;
    }

    @Override
    public List<EarningResponse> getEarningsCostsByDates(LocalDate startDate, LocalDate endDate) {
        
        List<EarningResponse> earningsCosts = new ArrayList<>();
        
        List<BillResponse> bills = this.reportsRestApiOutputAdapter.getAllBillsByDates(startDate, endDate);
        for (BillResponse bill : bills) {
            EarningResponse earning = new EarningResponse(bill.getId(),"Earning: " + bill.getType(), bill.getIdLocation(),bill.getStartDate(),bill.getTotal());
            earningsCosts.add(earning);
        }
        
        List<PaymentResponse> payments = this.reportsRestApiOutputAdapter.findPaymentsBetweenDates(startDate, endDate);
        for (PaymentResponse payment : payments) {
            EarningResponse cost = new EarningResponse(payment.getId(),"Payment, employee", payment.getEmployeeId(),payment.getDatePayment(),payment.getPaymentAmount());
            earningsCosts.add(cost);
        }
        
        List<MaintenanceResponse> maintenances = this.reportsRestApiOutputAdapter.findAllMaintenances(startDate, endDate);
        for (MaintenanceResponse maintenance : maintenances) {
            EarningResponse cost = new EarningResponse(maintenance.getIdMaintenance(),"Maintenance Room"
                , maintenance.getRoomId() , maintenance.getDateMaintenance(),maintenance.getMaintenanceCost());
            earningsCosts.add(cost);
        }
        
        return earningsCosts;
    }

}
