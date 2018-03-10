package com.fly.easy.flyeasy.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.function.BiConsumer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.api.dto.UpdateUserInformationDto;
import com.fly.easy.flyeasy.api.dto.UserDto;
import com.fly.easy.flyeasy.db.model.Picture;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.db.model.UserRole;
import com.fly.easy.flyeasy.db.model.UserRoleEnum;
import com.fly.easy.flyeasy.db.model.UserRolePk;
import com.fly.easy.flyeasy.db.model.VerificationToken;
import com.fly.easy.flyeasy.db.repository.PictureRepository;
import com.fly.easy.flyeasy.db.repository.UserRepository;
import com.fly.easy.flyeasy.db.repository.UserRoleRepository;
import com.fly.easy.flyeasy.service.interfaces.LocationService;
import com.fly.easy.flyeasy.service.interfaces.MailService;
import com.fly.easy.flyeasy.service.interfaces.PictureService;
import com.fly.easy.flyeasy.service.interfaces.UserService;
import com.fly.easy.flyeasy.service.interfaces.VerificationTokenService;
import com.fly.easy.flyeasy.util.PictureUtil;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

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
	
	@Autowired
	private MailService mailService;

	@Autowired
	private PictureService pictureService;

	@Autowired
	private PictureRepository pictureRepository;

	@Autowired
	private LocationService locationService;

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
    public UserDto register(UserDto userDto) throws ParseException,CannotSendEmailException {

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
	
	private UserDto processRegularRegistration(UserDto userDto) throws CannotSendEmailException {
		User savedUser;
		
		User userModel = mapper.map(userDto, User.class);
		userModel.setEnabled(false);
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userModel.setUserName(userDto.getUserName());
		userModel.setTimestamp(new Date());

		savedUser = userRepository.save(userModel);
		savedUser = addRole(savedUser, UserRoleEnum.USER);
		
		VerificationToken token = verificationTokenService.generateTokenForUser(savedUser);
		String url = verificationTokenService.urlFromToken(token);

		mailService.sendEmailConfirmation(savedUser.getEmail(), url);

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

	@Override
	public void resetPasswrodRequest(String userEmail) throws CannotSendEmailException {

		User user = userRepository.findByEmail(userEmail);
		if (user == null) {
			throw new ApiException("User not found with such and email");

		}
		generateToken(user);
	}

	private void generateToken(User user) throws CannotSendEmailException {

		VerificationToken oldToken = verificationTokenService.findByUser(user);
		if (oldToken != null) {
			verificationTokenService.deleteToken(oldToken);
		}

		VerificationToken newToken = verificationTokenService.generateTokenForUser(user);
		String url = verificationTokenService.urlFromToken(newToken);

		mailService.sendEmailResetPassord(user.getEmail(), url);

	}

	@Override
	public UserDto uploadProfilePhoto(MultipartFile file, UserDto userDto) throws IOException {
		User user = setUserProfilePicture(file, userDto, User::setProfilePicture);

		return UserDto.of(user);
	}

	private User setUserProfilePicture(MultipartFile file, UserDto userDto, BiConsumer<User, Picture> pictureSetter)
			throws IOException {
		PictureDto savePicure = pictureService.savePicure(PictureUtil.getImageFromMultipartFile(file),
				userDto.getUserName());
		User user = userRepository.findOne(userDto.getId());
		pictureSetter.accept(user, pictureRepository.findOne(savePicure.getId()));
		User saveUser = userRepository.saveAndFlush(user);

		return saveUser;

	}

	@Override
	public UpdateUserInformationDto updateUserInformation(UpdateUserInformationDto dto, long userId) {

		User user = userRepository.findOne(userId);

		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}

		if (dto.getFullName() != null) {
			user.setFullName(dto.getFullName());
		}

		if (dto.getBirthDate() != null) {
			user.setBirthDate(dto.getBirthDate());
		}

		if (dto.getLocation() != null) {
			user.setLocation(locationService.createLocation(dto.getLocation()));
		}

		User saveUser = userRepository.saveAndFlush(user);

		return UpdateUserInformationDto.of(saveUser);
	}

}
