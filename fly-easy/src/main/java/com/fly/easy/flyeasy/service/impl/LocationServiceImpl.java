package com.fly.easy.flyeasy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.db.model.Location;
import com.fly.easy.flyeasy.db.repository.LocationRepository;
import com.fly.easy.flyeasy.service.interfaces.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Location createLocation(String name) {

		Location location = locationRepository.findByName(name);
		Location saveLocation = null;

		if (location == null) {
			Location newLocation = new Location();
			newLocation.setName(name);

			saveLocation = locationRepository.saveAndFlush(newLocation);
		}

		return saveLocation == null ? location : saveLocation;
	}

}
