package com.fly.easy.flyeasy.api.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.dto.HotelBookingDto;
import com.fly.easy.flyeasy.api.dto.HotelDto;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.service.interfaces.HotelService;
import com.fly.easy.flyeasy.util.UserUtil;

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

	@RequestMapping(method = RequestMethod.POST, value = "/uploadPictures/{hotelId}")
	public ResponseEntity<List<PictureDto>> uploadProfilePhoto(@PathVariable("hotelId") long hotelId,
			@RequestParam("files") MultipartFile[] files) {

		List<PictureDto> dtos = new ArrayList<>();

		try {
			dtos = hotelService.uploadHotelPicutures(hotelId, files);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<List<HotelDto>> findAllHotel() {

		List<HotelDto> dtos = hotelService.findAllHotels();

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allMyBookedHotels")
	public ResponseEntity<List<HotelBookingDto>> findAllMyBookedHotels(SecurityContextHolder contex) {

		List<HotelBookingDto> dtos = hotelService.findAllMyBookedHotel(UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}
