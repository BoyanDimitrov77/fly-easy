package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;

import com.fly.easy.flyeasy.db.model.Airline;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirlineDto {

	private long id;
	private String airlineName;
	private BigDecimal rating;
	private String description;
	private PictureDto logo;

	public AirlineDto() {
		super();
	}

	public AirlineDto(long id, String airlineName, BigDecimal rating, String description, PictureDto logo) {
		super();
		this.id = id;
		this.airlineName = airlineName;
		this.rating = rating;
		this.description = description;
		this.logo = logo;
	}

	public static AirlineDto of(Airline airline) {
		return FlyEasyApp.ofNullable(airline,
				airl -> AirlineDto.builder().id(airl.getId()).airlineName(airl.getAirlineName())
						.rating(airl.getRating()).description(airl.getDescription()).logo(PictureDto.of(airl.getLogo()))
						.build());
	}



}
