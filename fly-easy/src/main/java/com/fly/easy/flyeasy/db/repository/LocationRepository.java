package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

	Location findByName(String name);
}
