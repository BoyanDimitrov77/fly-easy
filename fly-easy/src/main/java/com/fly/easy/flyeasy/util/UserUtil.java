package com.fly.easy.flyeasy.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.fly.easy.flyeasy.api.dto.UserDto;

public class UserUtil {

	public static UserDto gerUserFromContext(){
		return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
