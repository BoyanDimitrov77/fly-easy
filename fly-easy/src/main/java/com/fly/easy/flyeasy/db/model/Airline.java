package com.fly.easy.flyeasy.db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "airline")
public class Airline {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", nullable = false)
	private String airlineName;

	@Column(name = "rating")
	private BigDecimal rating;

}
