package com.fly.easy.flyeasy.service.interfaces;

import java.util.List;

import com.fly.easy.flyeasy.api.dto.AuthorityDto;

public interface UserRoleService {

	List<AuthorityDto> getUserRoles(long id);
}
