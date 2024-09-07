package com.eatsleep.user.client.infrastructure.outputadapters.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDbEntityRepository extends JpaRepository<ClientDbEntity, String>{
    Optional<ClientDbEntity> findByEmail(String email);
}
