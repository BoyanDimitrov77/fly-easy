package com.fly.easy.flyeasy.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class ChangeUserPasswordDto {

	private String oldPassword;
	private String newPassword;

	@Tolerate
	public ChangeUserPasswordDto() {

	}
}