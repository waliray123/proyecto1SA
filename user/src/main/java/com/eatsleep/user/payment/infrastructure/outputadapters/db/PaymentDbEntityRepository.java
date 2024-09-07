package com.eatsleep.user.payment.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDbEntityRepository extends JpaRepository<PaymentDbEntity, String>{
    
}
