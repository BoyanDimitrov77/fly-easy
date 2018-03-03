package com.fly.easy.flyeasy.api.dto;

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

	public TravelClassDto() {
		super();
	}

	public TravelClassDto(long id, int maxSeats, String travelClass) {
		super();
		this.id = id;
		this.maxSeats = maxSeats;
		this.travelClass = travelClass;
	}

	public static List<TravelClassDto> of(List<TravelClass> travelClasses) {
		return travelClasses.stream()
				.map(tc -> FlyEasyApp.ofNullable(tc, c -> TravelClassDto.builder().id(c.getId())
						.maxSeats(c.getMaxSeats()).travelClass(c.getTravelClass()).build()))
				.collect(Collectors.toList());
	}

}
