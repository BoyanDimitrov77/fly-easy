package com.fly.easy.flyeasy.service.impl;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.AirlineDto;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.db.model.Airline;
import com.fly.easy.flyeasy.db.model.Picture;
import com.fly.easy.flyeasy.db.repository.AirlineRepository;
import com.fly.easy.flyeasy.db.repository.PictureRepository;
import com.fly.easy.flyeasy.service.interfaces.AirlineService;
import com.fly.easy.flyeasy.service.interfaces.PictureService;
import com.fly.easy.flyeasy.util.PictureUtil;

@Service
public class ArilineServiceImpl implements AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private PictureRepository pictureRepository;

	@Override
	public AirlineDto createAirline(AirlineDto airlineDto) {
		Airline airline = new Airline();
		airline.setAirlineName(airlineDto.getAirlineName());

		if (airlineDto.getDescription() != null) {
			airline.setDescription(airlineDto.getDescription());
		}

		Airline saveAirline = airlineRepository.saveAndFlush(airline);

		return AirlineDto.of(saveAirline);
	}

	@Override
	public AirlineDto uploadAirlineLogo(MultipartFile logo, long airlineId) throws IOException {
		Airline airline = airlineRepository.findById(airlineId);

		if (airline == null) {
			throw new ApiException("Airline not found");
		}

		Airline savedAirline = setAirlineLogo(logo, airline, Airline::setLogo);

		return AirlineDto.of(savedAirline);
	}

	private Airline setAirlineLogo(MultipartFile logo, Airline airline, BiConsumer<Airline, Picture> logoSetter)
			throws IOException {

		PictureDto saveLogo = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(logo),
				airline.getAirlineName());
		logoSetter.accept(airline, pictureRepository.findOne(saveLogo.getId()));

		Airline savedAirline = airlineRepository.saveAndFlush(airline);

		return savedAirline;

	}

}
