package com.fly.easy.flyeasy.service.interfaces;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.dto.UpdateUserInformationDto;
import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.UserRoleEnum;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;


public interface UserService {

	UserDto findByEmail(String email);

	UserDto register(UserDto userDto) throws ParseException, CannotSendEmailException;
	
	User addRole(User user, UserRoleEnum role);
	
	User activateUser(User user);
	
	void resetPassword(User dbUser, String password);

	void resetPasswrodRequest(String userEmail) throws CannotSendEmailException;

	UserDto uploadProfilePhoto(MultipartFile file, UserDto userDto )throws IOException;

	UpdateUserInformationDto updateUserInformation(UpdateUserInformationDto dto , long userId);

	UserDto findUser(long userId);
}
