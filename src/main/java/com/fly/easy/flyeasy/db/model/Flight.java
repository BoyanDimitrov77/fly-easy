package com.fly.easy.flyeasy.db.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "flight")
public class Flight {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_id_seq")
	@SequenceGenerator(name = "flight_id_seq", sequenceName = "flight_id_seq", allocationSize = 1)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "depart_date",nullable = false)
	private Date departDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrive_date",nullable = false)
	private Date arriveDate;

	@Column(name = "price")
	private BigDecimal price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name="travel_class_flight",
			joinColumns = @JoinColumn(name = "flight_id"),
			inverseJoinColumns = @JoinColumn(name = "travel_class_id"))
	private List<TravelClass> travelClasses;

}
