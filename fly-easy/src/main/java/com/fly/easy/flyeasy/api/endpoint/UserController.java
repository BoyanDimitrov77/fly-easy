package com.fly.easy.flyeasy.api.endpoint;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users", produces = "application/json")
public class UserController {
	
	
	@RequestMapping(method = RequestMethod.GET,value="/hello")
	public String helloTest(SecurityContextHolder contex){
		
		return "Hello";
	}

}
