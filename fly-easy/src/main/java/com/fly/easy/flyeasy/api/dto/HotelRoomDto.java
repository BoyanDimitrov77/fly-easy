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

	private BigDecimal price;

	public HotelRoomDto() {
		super();
	}

	public HotelRoomDto(long id, String typeRoom, BigDecimal price) {
		super();
		this.id = id;
		this.typeRoom = typeRoom;
		this.price = price;
	}

	public static HotelRoomDto of(HotelRoom hotelRoom) {
		return FlyEasyApp.ofNullable(hotelRoom,
				hr -> HotelRoomDto.builder().id(hr.getId()).typeRoom(hr.getTypeRoom()).price(hr.getPrice()).build());
	}

}
