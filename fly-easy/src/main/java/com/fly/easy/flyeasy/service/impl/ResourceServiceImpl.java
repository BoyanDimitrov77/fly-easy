package com.fly.easy.flyeasy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.db.model.Resource;
import com.fly.easy.flyeasy.db.repository.ResourceRepository;
import com.fly.easy.flyeasy.service.interfaces.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public Resource createResource(String id, String value) {

		Resource resource = new Resource();
		resource.setId(id);
		resource.setValue(value);

		Resource savedResource = resourceRepository.saveAndFlush(resource);

		return savedResource;
	}

}
