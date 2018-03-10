package com.fly.easy.flyeasy.api.endpoint;

import java.io.IOException;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.AirlineDto;
import com.fly.easy.flyeasy.service.interfaces.AirlineService;

@RestController
@RequestMapping(value = "airline", produces = "application/json")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;

	@RequestMapping(method = RequestMethod.POST, value = "/createAirlineRecord")
	@Transactional
	public ResponseEntity<AirlineDto> createAirlineRecord(@RequestBody AirlineDto airlineDto) {

		AirlineDto dto = airlineService.createAirline(airlineDto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadAirlineLogo/{airlineId}")
	@Transactional
	public ResponseEntity<AirlineDto> uploadAirlineLog(@RequestParam("file") MultipartFile file,
			@PathVariable("airlineId") long airlineId) {

		AirlineDto airlineDto = null;
		try {
			airlineDto = airlineService.uploadAirlineLogo(file, airlineId);
		} catch (IOException e) {
			throw new ApiException(e);
		}

		return new ResponseEntity<>(airlineDto, HttpStatus.OK);
	}

}
