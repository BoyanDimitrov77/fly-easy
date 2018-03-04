package com.fly.easy.flyeasy.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.fly.easy.flyeasy.api.dto.HotelBookingDto;
import com.fly.easy.flyeasy.api.dto.HotelDto;

public interface HotelService {

	List<HotelDto> findHotelsByCurrentDestionation(long locationId);

	HotelDto getHotel(long hotelId);

	HotelBookingDto bookHotel(long hotelRoomId,long userId);

	HotelBookingDto payHotel(long hotelBookId,BigDecimal amount);

	HotelDto createHotel(HotelDto hotelDto);

}
