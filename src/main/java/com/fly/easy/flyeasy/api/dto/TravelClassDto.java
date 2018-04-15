package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.fly.easy.flyeasy.db.model.TravelClass;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelClassDto {

	private long id;

	private int maxSeats;

	private String travelClass;

	private BigDecimal price;

	public TravelClassDto() {
		super();
	}

	public TravelClassDto(long id, int maxSeats, String travelClass,BigDecimal price) {
		super();
		this.id = id;
		this.maxSeats = maxSeats;
		this.travelClass = travelClass;
		this.price = price;
	}

	public static List<TravelClassDto> of(List<TravelClass> travelClasses) {
		return travelClasses.stream()
				.map(tc -> FlyEasyApp.ofNullable(tc, c -> TravelClassDto.builder().id(c.getId())
						.maxSeats(c.getMaxSeats()).travelClass(c.getTravelClass())
						.price(c.getPrice()).build()))
				.collect(Collectors.toList());
	}

}
