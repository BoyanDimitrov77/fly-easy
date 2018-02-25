package com.fly.easy.flyeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fly.easy.flyeasy.db.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	
	Flight findById(long id);

	@Query("SELECT fl FROM Flight fl WHERE fl.departDate>CURDATE() ORDER BY fl.departDate")
	List<Flight> findAllFlights();
}
