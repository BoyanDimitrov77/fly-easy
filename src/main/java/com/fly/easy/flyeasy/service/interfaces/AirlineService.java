package com.fly.easy.flyeasy.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.dto.AirlineDto;

public interface AirlineService {

	AirlineDto createAirline(AirlineDto airlineDto);

	AirlineDto uploadAirlineLogo(MultipartFile logo , long airlineId) throws IOException;

}
