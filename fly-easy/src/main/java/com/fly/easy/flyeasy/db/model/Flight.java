package com.fly.easy.flyeasy.db.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "flight")
public class Flight {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "airline_id")
	private Airline airline;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_from_id")
	private Location locationFrom;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_to_id")
	private Location locationTo;

	@Column(name = "depart_date")
	private Date departDate;

	@Column(name = "arrive_date")
	private Date arriveDate;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "seats")
	private int seats;

	@Column(name = "class")
	private String travelClass;

	@Column(name = "timestamp")
	private Date timestamp;

}
