package com.eatsleep.bill.billdescription.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDescriptionDbEntityRepository extends JpaRepository<BillDescriptionDbEntity, String>{

}
