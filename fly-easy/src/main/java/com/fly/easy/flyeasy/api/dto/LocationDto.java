package com.fly.easy.flyeasy.api.dto;

import com.fly.easy.flyeasy.db.model.Location;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {
	
	private long id;
	private String name;

	public LocationDto() {
		super();
	}

	public LocationDto(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static LocationDto of (Location location){
		return FlyEasyApp.ofNullable(location, l ->LocationDto
				.builder()
				.id(l.getId())
				.name(l.getName())
				.build());
	}
	
}
