package com.fly.easy.flyeasy.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterLocationDto {

	private String locationFrom;

	private String locationTo;

	public FilterLocationDto() {
		super();
	}

	public FilterLocationDto(String locationFrom, String locationTo) {
		super();
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
	}

}
