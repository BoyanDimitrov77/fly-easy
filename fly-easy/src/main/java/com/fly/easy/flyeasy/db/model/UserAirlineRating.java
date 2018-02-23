package com.fly.easy.flyeasy.db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user_airline_rating")
public class UserAirlineRating {

	@EmbeddedId
	private UserAirlineRatingPk id;

	@Column(name = "rating")
	private BigDecimal rating;

}
