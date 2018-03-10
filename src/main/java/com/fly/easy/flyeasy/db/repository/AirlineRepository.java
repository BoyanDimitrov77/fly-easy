package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long>{

	Airline findById(long id);
}
