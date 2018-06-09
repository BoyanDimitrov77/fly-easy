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
public class ReservedHotelDto {

	private long id;

	private String hotelName;

	private LocationDto location;

	private List<PictureDto> pictures;

	public ReservedHotelDto() {
		super();
	}

	public ReservedHotelDto(long id, String hotelName, LocationDto location, List<PictureDto> pictures) {
		super();
		this.id = id;
		this.hotelName = hotelName;
		this.location = location;
		this.pictures = pictures;

	}

	public static ReservedHotelDto of(Hotel hotel) {
		return FlyEasyApp.ofNullable(hotel, h -> ReservedHotelDto.builder().id(h.getId()).hotelName(h.getHotelName())
				.location(LocationDto.of(h.getLocation()))
				.pictures(h.getHotelPictures() != null ? h.getHotelPictures().stream()
						.map(picture -> PictureDto.of(picture)).collect(Collectors.toList()) : new ArrayList<>())
				.build());
	}

}
