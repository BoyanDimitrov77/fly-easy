package com.fly.easy.flyeasy.api.dto;

import com.fly.easy.flyeasy.db.model.Picture;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PictureDto {

	private String id;

	private ResourceDto orignalPicture;

	private ResourceDto thumbnailPicture;

	private ResourceDto fullScreenPicture;

	public static final PictureDto of(Picture picture) {
		return PictureDto.builder().id(picture.getId()).orignalPicture(ResourceDto.of(picture.getOriginalImage()))
				.thumbnailPicture(ResourceDto.of(picture.getThumbnailPicture()))
				.fullScreenPicture(ResourceDto.of(picture.getFullScreenPicutre())).build();

	}
}
