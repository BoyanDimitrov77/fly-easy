package com.fly.easy.flyeasy.api.dto;

import java.security.Principal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fly.easy.flyeasy.db.model.User;
import com.fly.easy.flyeasy.util.FlyEasyApp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@ToString
@EqualsAndHashCode
@Builder
public class UserDto implements Principal {

	@Getter
	@Setter
	private long id;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String userName;

	@Getter
	@Setter
	private String fullName;

	@Getter
	@Setter
	private boolean enabled;

	private String password;

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

    @Getter
    @Setter
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date timestamp;

    @Getter
    @Setter
    private PictureDto profilePicture;

    @Tolerate
	public UserDto() {
	}

    @Override
    public String getName() {
        return fullName;
    }

    public static UserDto of(User user) {

	return FlyEasyApp.ofNullable(user, u->UserDto.builder()
				.id(u.getId())
                .email(u.getEmail())
                .userName(u.getUserName())
                .fullName(u.getFullName())
                .enabled(u.isEnabled())
                .timestamp(u.getTimestamp())
                .profilePicture(PictureDto.of(u.getProfilePicture()))
                .build());
    }
}
