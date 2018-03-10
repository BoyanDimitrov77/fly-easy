package com.fly.easy.flyeasy.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fly.easy.flyeasy.db.model.PassengerTicket;

public interface PassengerTicketRepository extends JpaRepository<PassengerTicket, Long> {

	PassengerTicket findByTicketNumber(String ticketNumber);
}
