package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
