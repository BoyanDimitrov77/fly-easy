package com.fly.easy.flyeasy.api.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.util.UserUtil;

@RestController
@RequestMapping(value = "authenticate")
public class AuthenticationController {

	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserDto> authenticate(SecurityContextHolder context) {
    	
        return new ResponseEntity<>(UserUtil.gerUserFromContext(), HttpStatus.OK);
    }
	
}
