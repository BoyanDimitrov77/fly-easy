package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.easy.flyeasy.db.model.Flight;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDto {

	private long id;
	private AirlineDto airLine;
	private String flightName;
	private LocationDto locationFrom;
	private LocationDto locationTo;
	
	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm", timezone = "UTC")
	private Date departDate;

	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm", timezone = "UTC")
	private Date arriveDate;

	private BigDecimal price;

	private List<TravelClassDto> travelClasses;

	public FlightDto() {
		super();

	}

	public FlightDto(long id, AirlineDto airLine, String flightName, LocationDto locationFrom, LocationDto locationTo,
			Date departDate, Date arriveDate, BigDecimal price,	List<TravelClassDto> travelClasses) {
		super();
		this.id = id;
		this.airLine = airLine;
		this.flightName = flightName;
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
		this.departDate = departDate;
		this.arriveDate = arriveDate;
		this.price = price;
		this.travelClasses = travelClasses;
	}

	public static FlightDto of(Flight flight) {
		return FlyEasyApp.ofNullable(flight,
				f -> FlightDto.builder().id(f.getId()).airLine(AirlineDto.of(f.getAirline())).flightName(f.getName())
						.locationFrom(LocationDto.of(f.getLocationFrom())).locationTo(LocationDto.of(f.getLocationTo()))
						.departDate(f.getDepartDate()).arriveDate(f.getArriveDate()).price(f.getPrice())
						.travelClasses(TravelClassDto.of(f.getTravelClasses()))
						.build());
	}

}
