package com.fly.easy.flyeasy.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fly.easy.flyeasy.db.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	
	Flight findById(long id);

	@Query("SELECT fl FROM Flight fl WHERE fl.departDate>=NOW() ORDER BY fl.departDate")
	List<Flight> findAllFlights();

	@Query("SELECT fl FROM Flight fl WHERE Date(fl.departDate) >= Date(:fromDate) and Date(fl.departDate) <= Date(:toDate) ORDER BY fl.departDate")
	List<Flight> findAllFlightBetweenDates(@Param("fromDate")Date fromDate ,@Param("toDate") Date toDate);

	@Query("SELECT fl FROM Flight fl WHERE fl.departDate>=NOW() ORDER BY fl.price ASC")
	List<Flight> findFlightsByPrice();

	@Query("SELECT fl FROM Flight fl WHERE fl.locationFrom.name = :locationFrom AND fl.locationTo.name = :locationTo ORDER BY fl.departDate")
	List<Flight> findFlightsByLocation(@Param("locationFrom") String locationFrom,@Param("locationTo") String locationTo);

	@Query("SELECT fl FROM Flight fl WHERE fl.departDate>=NOW() ORDER BY fl.airline.rating DESC")
	List<Flight> findFlightsByRatingAirline();

}
