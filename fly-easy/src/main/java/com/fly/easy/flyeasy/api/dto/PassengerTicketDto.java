package com.fly.easy.flyeasy.api.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fly.easy.flyeasy.db.model.PassengerTicket;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerTicketDto {

	private long id;

	private String passengerName;

	private String identificationNumber;

	private String email;

	private String ticketNumber;

	private String boardSeatNumber;

	public PassengerTicketDto() {
		super();
	}

	public PassengerTicketDto(long id, String passengerName, String identificationNumber, String email,
			String ticketNumber, String boardSeatNumber) {
		super();
		this.id = id;
		this.passengerName = passengerName;
		this.identificationNumber = identificationNumber;
		this.email = email;
		this.ticketNumber = ticketNumber;
		this.boardSeatNumber = boardSeatNumber;
	}

	public static List<PassengerTicketDto> of(List<PassengerTicket> passengerTickets) {
		return passengerTickets.stream()
				.map(pt -> FlyEasyApp.ofNullable(pt,
						t -> PassengerTicketDto.builder().id(t.getId()).passengerName(t.getPassengerName())
								.identificationNumber(t.getPersonalIdentificationNumber())
								.ticketNumber(t.getTicketNumber()).boardSeatNumber(t.getBoardSeatNumber())
								.email(t.getEmail()).boardSeatNumber(t.getBoardSeatNumber()).build()))
				.collect(Collectors.toList());
	}

}
