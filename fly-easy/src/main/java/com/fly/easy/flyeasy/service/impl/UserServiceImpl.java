package com.fly.easy.flyeasy.service.impl;

import java.text.ParseException;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.UserRole;
import com.fly.easy.flyeasy.db.model.UserRoleEnum;
import com.fly.easy.flyeasy.db.model.UserRolePk;
import com.fly.easy.flyeasy.db.model.VerificationToken;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.db.repository.UserRoleRepository;
import com.fly.easy.flyeasy.service.interfaces.UserService;
import com.fly.easy.flyeasy.service.interfaces.VerificationTokenService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VerificationTokenService verificationTokenService;
	
	public PasswordEncoder getPasswordEncoder(){
		return this.passwordEncoder;
	}
	
	@Override
    public UserDto findByEmail(String email) {
		UserDto result = mapper.map(userRepository.findByEmail(email), UserDto.class);
		return result;
	}

	@Override
	@Transactional
    public UserDto register(UserDto userDto) throws ParseException {

        /*if (!userDtoValidator.isValid(userDto)) {
            throw new ApiException("User with email already exists");
        }
	    if (userDto.getToken() != null) { // Sign up with facebook
	        return processFacebookSignup(userDto);
	    } else {
	        return processRegularRegistration(userDto);
	    }*/
		
		return processRegularRegistration(userDto);
	}
	
	private UserDto processRegularRegistration(UserDto userDto) {
		User savedUser;
		
		User userModel = mapper.map(userDto, User.class);
		userModel.setEnabled(false);
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userModel.setUserName(userDto.getUserName());

		savedUser = userRepository.save(userModel);
		savedUser = addRole(savedUser, UserRoleEnum.ROLE_USER);
		
		VerificationToken token = verificationTokenService.generateTokenForUser(savedUser);
		String url = verificationTokenService.urlFromToken(token.getToken(), "confirm");

		//mailService.sendAccountConfirmationMail(savedUser.getEmail(), url);

		return UserDto.of(savedUser);
	}
	
	@Override
    public User addRole(User user, UserRoleEnum role) {
		UserRole userRole = new UserRole();
		userRole.setId(UserRolePk.builder().user(user).role(role).build());
		userRoleRepository.save(userRole);
		User result = userRepository.findByEmail(user.getEmail());
		return result;
	}
	
	@Override
    public User activateUser(User user) {

	    user.setEnabled(true);
		userRepository.save(user);

		return user;
	}
	
	@Override
    public void resetPassword(User dbUser, String password) {
		dbUser.setPassword(passwordEncoder.encode(password));
		userRepository.save(dbUser);
	}

/*	@Override
	public boolean isMatchPassword(String inputPassword, String userPassword) {
		
		return passwordEncoder.matches(inputPassword, userPassword);
	}*/
}
