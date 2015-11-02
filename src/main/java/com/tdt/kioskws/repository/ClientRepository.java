package com.tdt.kioskws.repository;

import com.tdt.kioskws.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ClientRepository
 */
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByClientIDAndClientSecret(String clientID, String clientSecret);
    @Query("select client from ClientMapper m where m.key = :key")
    Client findByKey(@Param("key")String key);
}
