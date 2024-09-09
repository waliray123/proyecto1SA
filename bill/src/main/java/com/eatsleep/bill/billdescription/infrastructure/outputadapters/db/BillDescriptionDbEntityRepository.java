package com.eatsleep.bill.billdescription.infrastructure.outputadapters.db;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDescriptionDbEntityRepository extends JpaRepository<BillDescriptionDbEntity, String>{
    @Query(value = "SELECT id_product " +
               "FROM bill_description " +
               "WHERE type = 'room' " +
               "GROUP BY id_product " +
               "ORDER BY SUM(quantity) DESC " +
               "LIMIT 1", 
       nativeQuery = true)
    String findRoomWithMaxQuantity();
    
    @Query(
        value = "SELECT bd.id_product " +
                "FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE bd.type = 'room' " +
                "AND b.id_location = :idLocation " +
                "GROUP BY bd.id_product " +
                "ORDER BY SUM(bd.quantity) DESC " +
                "LIMIT 1",
        nativeQuery = true
    )
    String findRoomWithMaxQuantityByLocation(@Param("idLocation") String idLocation);
    
    @Query(
        value = "SELECT id_product " +
                "FROM bill_description " +
                "WHERE type = 'dish' " +
                "GROUP BY id_product " +
                "ORDER BY SUM(quantity * unit_price) DESC " +
                "LIMIT 1",
        nativeQuery = true
    )
    String findDishWithMaxSale();

    @Query(
        value = "SELECT bd.id_product " +
                "FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE b.id_location = :idLocation " +
                "AND bd.type = 'dish' " +
                "GROUP BY bd.id_product " +
                "ORDER BY SUM(bd.quantity * bd.unit_price) DESC " +
                "LIMIT 1",
        nativeQuery = true
    )
    String findDishWithMaxSalesByLocation(@Param("idLocation") String idLocation);
    
    @Query(
        value = "SELECT * " +
                "FROM bill_description " +
                "WHERE type = 'room' " +
                "AND id_product = :idRoom",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> findAllByRoomId(@Param("idRoom") String idRoom);
    
    @Query(
        value = "SELECT * " +
                "FROM bill_description " +
                "WHERE type = 'dish' " +
                "AND id_product = :idDish",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> findAllByDishId(@Param("idDish") String idDish);
    
    @Query(
        value = "SELECT bd.* " +
                "FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE b.id_location = :idLocation " +
                "AND b.start_date >= :startDate " +
                "AND b.end_date <= :endDate",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> getBillDescriptionsByIdLocationByDates(
            @Param("idLocation") String idLocation, 
            @Param("startDate") LocalDate startDate, 
            @Param("endDate") LocalDate endDate
    );
    
    
    @Query(
        value = "SELECT bd.* FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE b.id_location = :idLocation",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> getBillDescriptionsByIdLocationAllTime(@Param("idLocation") String idLocation);
    
    @Query(
        value = "SELECT bd.* FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE b.user_id = :idClient " +
                "AND b.start_date BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> getBillDescriptionsByIdClient(
        @Param("idClient") String idClient,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query(
        value = "SELECT bd.* FROM bill_description bd " +
                "JOIN bill b ON bd.bill_id = b.id " +
                "WHERE b.user_id = :idClient " +
                "AND b.id_location = :idLocation " +
                "AND b.start_date BETWEEN :startDate AND :endDate",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> getBillDescriptionsByIdClientAndIdLocation(
        @Param("idClient") String idClient,
        @Param("idLocation") String idLocation,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    @Query(
        value = "SELECT * FROM bill_description WHERE bill_id = :idBill",
        nativeQuery = true
    )
    List<BillDescriptionDbEntity> findBillDescriptionsByIdBill(@Param("idBill") String idBill);
    
}

