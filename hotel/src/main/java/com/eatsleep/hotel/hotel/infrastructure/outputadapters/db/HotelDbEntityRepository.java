package com.eatsleep.hotel.hotel.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDbEntityRepository extends JpaRepository<HotelDbEntity, String>{

}
