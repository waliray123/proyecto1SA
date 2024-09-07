package com.eatsleep.promotion.comment.infrastructure.outputadapters.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDbEntityRepository extends JpaRepository<CommentDbEntity, String>{
    List<CommentDbEntity> findByUser(String user);
    
    @Query(value = "SELECT id_product, MAX(rate) AS max_rate " +
                   "FROM promotiondb.comment " +
                   "WHERE type = 'room' " +
                   "GROUP BY id_product " +
                   "ORDER BY max_rate DESC", nativeQuery = true)
    List<Object[]> findTopRatedRoomComments();
    
    @Query(value = "SELECT id_product, MAX(rate) AS max_rate " +
                   "FROM promotiondb.comment " +
                   "WHERE type = 'dish' " +
                   "GROUP BY id_product " +
                   "ORDER BY max_rate DESC", nativeQuery = true)
    List<Object[]> findTopRatedDishComments();
}
