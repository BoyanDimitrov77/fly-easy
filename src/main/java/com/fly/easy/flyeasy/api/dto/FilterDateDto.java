package com.fly.easy.flyeasy.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterDateDto {

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date fromDate;

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date toDate;

	public FilterDateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FilterDateDto(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
}
