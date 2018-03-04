package com.fly.easy.flyeasy.api.endpoint;

import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.dto.HotelDto;
import com.fly.easy.flyeasy.service.interfaces.HotelService;

@RestController
@RequestMapping(value = "hotel", produces = "application/json")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@RequestMapping(method = RequestMethod.POST ,value ="/createHotel")
	@Transactional
	public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto){
		HotelDto dto = hotelService.createHotel(hotelDto);
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{hotelId}")
	public ResponseEntity<HotelDto> getHotel(@PathVariable("hotelId") long hotelId) {
		HotelDto hotelDto = hotelService.getHotel(hotelId);

		return new ResponseEntity<>(hotelDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/availableHotels/{locationId}")
	public ResponseEntity<List<HotelDto>> findAvailableHotelForCurrentDestination(
			@PathVariable("locationId") long locationId) {

		List<HotelDto> dtos = hotelService.findHotelsByCurrentDestionation(locationId);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
