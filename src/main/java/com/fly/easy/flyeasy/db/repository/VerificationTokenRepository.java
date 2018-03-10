package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	 public VerificationToken findByUser(User user);

	  public VerificationToken findByToken(String token);
}
