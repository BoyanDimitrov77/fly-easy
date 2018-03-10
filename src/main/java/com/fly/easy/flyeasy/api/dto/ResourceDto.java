package com.fly.easy.flyeasy.api.dto;

import java.util.Date;

import com.fly.easy.flyeasy.db.model.Resource;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResourceDto {

	private String id;

	private String value;

	//private Date timeCreated;

	public ResourceDto() {
		super();
	}

	public ResourceDto(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public static final ResourceDto of(Resource resource) {
		return ResourceDto.builder().id(resource.getId()).value(resource.getValue())
				//.timeCreated(resource.getTimeCreated())
				.build();
	}

}
