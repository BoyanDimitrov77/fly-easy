package com.fly.easy.flyeasy.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fly.easy.flyeasy.db.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	@Query("SELECT u FROM UserRole u WHERE u.id.user.id = :userId")
    public List<UserRole> findByUser(@Param("userId") long userId);
	
/*	@Query("Select u FROM UserRole u WHERE u.id.user.id = :userId AND u.id.role = :roleAdmin ")
    public UserRole findByIdAndRole(@Param("userId")long userId,@Param("roleAdmin")UserRoleEnum roleAdmin,@Param("roleEditor")UserRoleEnum roleEditor);*/
}
