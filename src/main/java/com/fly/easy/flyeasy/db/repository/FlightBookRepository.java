package com.fly.easy.flyeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fly.easy.flyeasy.db.model.FlightBook;

public interface FlightBookRepository extends JpaRepository<FlightBook, Long> {

	@Query("SELECT fb FROM FlightBook fb WHERE fb.booker.id =:userId")
	List<FlightBook> getMyFlights(@Param("userId") long userId);

}
