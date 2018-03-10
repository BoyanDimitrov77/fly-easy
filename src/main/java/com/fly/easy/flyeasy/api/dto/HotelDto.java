package com.fly.easy.flyeasy.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fly.easy.flyeasy.db.model.Hotel;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelDto {

	private long id;

	private String hotelName;

	private LocationDto location;

	private String description;

	private List<PictureDto> pictures;

	private List<HotelRoomDto> hotelRooms;

	public HotelDto() {
		super();
	}

	public HotelDto(long id, String hotelName, LocationDto location, String description, List<PictureDto> pictures,
			List<HotelRoomDto> hotelRooms) {
		super();
		this.id = id;
		this.hotelName = hotelName;
		this.location = location;
		this.description = description;
		this.pictures = pictures;
		this.hotelRooms = hotelRooms;
	}

	public static HotelDto of(Hotel hotel) {
		return FlyEasyApp.ofNullable(hotel,
				h -> HotelDto.builder().id(h.getId()).hotelName(h.getHotelName())
						.location(LocationDto.of(h.getLocation()))
						.description(h.getDescription())
						.pictures(h.getHotelPictures() != null ? h.getHotelPictures().stream()
								.map(picture -> PictureDto.of(picture)).collect(Collectors.toList()) : new ArrayList<>())
						.hotelRooms(h.getHotelRooms().stream().map(hotelRoom -> HotelRoomDto.of(hotelRoom))
								.collect(Collectors.toList()))
						.build());
	}

}
