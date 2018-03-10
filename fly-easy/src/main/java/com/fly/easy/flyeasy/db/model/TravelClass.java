package com.fly.easy.flyeasy.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "travel_class")
public class TravelClass {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_class_id_seq")
	@SequenceGenerator(name = "travel_class_id_seq", sequenceName = "travel_class_id_seq", allocationSize = 1)
	private long id;

	@Column(name = "max_seats")
	private int maxSeats;

	@Column(name = "count_seats")
	private int counterSeats;

	@Column(name = "travel_class")
	private String travelClass;
}
