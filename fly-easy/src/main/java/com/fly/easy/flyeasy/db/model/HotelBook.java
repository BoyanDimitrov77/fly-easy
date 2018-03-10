package com.fly.easy.flyeasy.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "hotel_book")
public class HotelBook {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_book_id_seq")
	@SequenceGenerator(name = "hotel_book_id_seq", sequenceName = "hotel_book_id_seq", allocationSize = 1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "hotel_room_id")
	private HotelRoom hotelRoom;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@Column(name = "status")
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

}
