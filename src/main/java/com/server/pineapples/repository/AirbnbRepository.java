package com.server.pineapples.repository;

import com.server.pineapples.entities.Airbnb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirbnbRepository extends JpaRepository<Airbnb, Long> {
}
