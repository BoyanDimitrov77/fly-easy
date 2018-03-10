package com.fly.easy.flyeasy.api.dto;

import com.fly.easy.flyeasy.db.model.HotelBook;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelBookingDto {

	private long id;

	private HotelRoomDto hotelRoom;

	private UserDto booker;

	private PaymentDto payment;

	private String status;

	public static HotelBookingDto of(HotelBook hotelBook) {
		return FlyEasyApp.ofNullable(hotelBook,
				hb -> HotelBookingDto.builder().id(hb.getId()).hotelRoom(HotelRoomDto.of(hb.getHotelRoom()))
						.booker(UserDto.of(hb.getUser())).payment(PaymentDto.of(hb.getPayment())).status(hb.getStatus())
						.build());
	}
}
