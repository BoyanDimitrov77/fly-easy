package com.fly.easy.flyeasy.api.dto;

import java.math.BigDecimal;

import com.fly.easy.flyeasy.db.model.HotelRoom;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelRoomDto {

	private long id;

	private String typeRoom;

	private ReservedHotelDto reservedHotel;

	private BigDecimal price;

	public HotelRoomDto() {
		super();
	}

	public HotelRoomDto(long id, String typeRoom, ReservedHotelDto hotelBooked, BigDecimal price) {
		super();
		this.id = id;
		this.typeRoom = typeRoom;
		this.reservedHotel = hotelBooked;
		this.price = price;
	}

	public static HotelRoomDto of(HotelRoom hotelRoom) {
		return FlyEasyApp.ofNullable(hotelRoom, hr -> HotelRoomDto.builder().id(hr.getId()).typeRoom(hr.getTypeRoom())
				.reservedHotel(ReservedHotelDto.of(hr.getHotel())).price(hr.getPrice()).build());
	}

}