package com.fly.easy.flyeasy.db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "hotel_room")
public class HotelRoom {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_room_id_seq")
	@SequenceGenerator(name = "hotel_room_id_seq", sequenceName = "hotel_room_id_seq", allocationSize = 1)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@Column(name = "type_room")
	private String typeRoom;

	@Column(name = "price")
	private BigDecimal price;

}
