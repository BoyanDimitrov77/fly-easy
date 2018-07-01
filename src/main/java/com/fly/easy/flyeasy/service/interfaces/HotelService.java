package com.fly.easy.flyeasy.service.interfaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.dto.HotelBookingDto;
import com.fly.easy.flyeasy.api.dto.HotelDto;
import com.fly.easy.flyeasy.api.dto.PictureDto;

public interface HotelService {

	List<HotelDto> findHotelsByCurrentDestionation(long locationId);

	HotelDto getHotel(long hotelId);

	HotelBookingDto bookHotel(long hotelRoomId,long userId);

	HotelBookingDto payHotel(long hotelBookId,BigDecimal amount);

	HotelDto createHotel(HotelDto hotelDto);

	List<PictureDto> uploadHotelPicutures(long hotelId, MultipartFile[] files) throws IOException ;

	List<HotelDto> findAllHotels();

	List<HotelBookingDto> findAllMyBookedHotel(long userId);
}
