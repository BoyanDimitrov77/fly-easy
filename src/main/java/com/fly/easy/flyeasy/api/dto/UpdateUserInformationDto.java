package com.fly.easy.flyeasy.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserInformationDto {

	private String fullName;

	private String location;

	private String email;

	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date birthDate;

	public UpdateUserInformationDto() {
		super();
	}

	public UpdateUserInformationDto(String fullName, String location, String email, Date birthDate) {
		super();
		this.fullName = fullName;
		this.location = location;
		this.email = email;
		this.birthDate = birthDate;
	}
	
	public static UpdateUserInformationDto of(User user) {
		return FlyEasyApp.ofNullable(user, u -> UpdateUserInformationDto.builder()
				.fullName(u.getFullName())
				.location(u.getLocation() != null ? u.getLocation().getName() : null)
				.email(u.getEmail())
				.birthDate(u.getBirthDate())
				.build());
	}

}
