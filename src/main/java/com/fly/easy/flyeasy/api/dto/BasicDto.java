package com.fly.easy.flyeasy.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicDto<T> {

	private T data;

	public BasicDto() {
		super();
	}

	public BasicDto(T data) {
		super();
		this.data = data;
	}

}
