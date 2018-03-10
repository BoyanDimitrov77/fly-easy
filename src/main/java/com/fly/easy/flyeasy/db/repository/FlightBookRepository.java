package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.FlightBook;

public interface FlightBookRepository extends JpaRepository<FlightBook, Long> {

}
