package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, String>{

}
