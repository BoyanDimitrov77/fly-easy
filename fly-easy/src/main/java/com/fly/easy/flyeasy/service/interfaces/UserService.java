package com.fly.easy.flyeasy.service.interfaces;

import java.text.ParseException;

import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.UserRoleEnum;


public interface UserService {

	UserDto findByEmail(String email);

	UserDto register(UserDto userDto) throws ParseException;
	
	User addRole(User user, UserRoleEnum role);
	
	User activateUser(User user);
	
	void resetPassword(User dbUser, String password);
	
	//boolean isMatchPassword(String inputPassword, String userPassword);
}
