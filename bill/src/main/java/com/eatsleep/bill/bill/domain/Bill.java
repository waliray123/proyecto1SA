package com.eatsleep.bill.bill.domain;

import com.eatsleep.bill.billdescription.domain.BillDescription;
import com.eatsleep.bill.common.DomainEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Bill {
    private UUID id;
    private String type;
    private UUID idLocation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total;
    private UUID userId;
    private UUID reservationId;
    private List<BillDescription> descriptions;

}
