package com.fly.easy.flyeasy.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.service.interfaces.UserService;

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
	public ResponseEntity<String> resetPassword(@RequestBody UserDto user) {

		try {
			userService.resetPasswrodRequest(user.getEmail());
		} catch (CannotSendEmailException e) {

			throw new ApiException(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
