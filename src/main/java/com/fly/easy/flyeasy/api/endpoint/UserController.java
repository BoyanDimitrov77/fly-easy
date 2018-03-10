package com.fly.easy.flyeasy.api.endpoint;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.api.dto.UpdateUserInformationDto;
import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.service.interfaces.UserService;
import com.fly.easy.flyeasy.util.UserUtil;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

@RestController
@RequestMapping(value = "users", produces = "application/json")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET,value="/hello")
	public String helloTest(SecurityContextHolder contex){
		
		return "Hello";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
	public ResponseEntity<String> RequestMethod(@RequestBody UserDto user) {

		try {
			userService.resetPasswrodRequest(user.getEmail());
		} catch (CannotSendEmailException e) {

			throw new ApiException(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadProfilePhoto")
	public ResponseEntity<PictureDto> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
			SecurityContextHolder context) {

		PictureDto profilePicture = null;
		try {
			profilePicture = userService.uploadProfilePhoto(file, UserUtil.gerUserFromContext()).getProfilePicture();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(profilePicture, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updatePersonalInformation")
	public ResponseEntity<UpdateUserInformationDto> updatePersonalInformation(@RequestBody UpdateUserInformationDto dto,
			SecurityContextHolder contex) {

		UpdateUserInformationDto updateUserInformationDto = userService.updateUserInformation(dto,
				UserUtil.gerUserFromContext().getId());

		return new ResponseEntity<>(updateUserInformationDto, HttpStatus.OK);

	}

}
