package com.fly.easy.flyeasy.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.api.common.ApiException;
import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.db.model.Picture;
import com.fly.easy.flyeasy.db.model.PictureSize;
import com.fly.easy.flyeasy.db.model.PictureType;
import com.fly.easy.flyeasy.db.model.Resource;
import com.fly.easy.flyeasy.db.repository.PictureRepository;
import com.fly.easy.flyeasy.service.interfaces.GoogleDriveService;
import com.fly.easy.flyeasy.service.interfaces.PictureService;
import com.fly.easy.flyeasy.service.interfaces.ResourceService;
import com.fly.easy.flyeasy.util.PictureInfo;
import com.fly.easy.flyeasy.util.PictureUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${image.size.thumbnail.picture.height}")
	private int thumbnailPicutreSizeHeight;

	@Value("${image.size.thumbnail.picture.width}")
	private int thumbnailPicutreSizeWidth;

	@Value("${image.size.fullscreen.picure.height}")
	private int fullScreenSizeHeight;

	@Value("${image.size.fullscreen.picture.width}")
	private int fullScreenSizeWidth;

	@Autowired
	private GoogleDriveService googleDriveService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private PictureRepository pictureRepository;

	@Override
	public PictureDto savePicure(PictureInfo image, String path) throws IOException {
		Picture createPicture = createPicture(image, path);
		Picture savedPicture = pictureRepository.saveAndFlush(createPicture);

		return PictureDto.of(savedPicture);
	}

	private Picture createPicture(PictureInfo image, String path) throws IOException {

		Resource savedOriginImage = saveImage(image, path, PictureType.ORIGIN);
		Resource savedThumbnailImage = saveImage(image, path, PictureType.THUMBNAIL);
		Resource savedFullScreenImage = saveImage(image, path, PictureType.FULLSCREEN);

		Picture picture = new Picture();
		picture.setOriginalImage(savedOriginImage);
		picture.setThumbnailPicture(savedThumbnailImage);
		picture.setFullScreenPicutre(savedFullScreenImage);

		return picture;
	}

	private Resource saveImage(PictureInfo image, String path, PictureType type) throws IOException {

		BufferedImage currentImage = image.getImage();
		BufferedImage resizeImage = resizeImage(currentImage, type);
		String imageFormat = image.getType();

		String resourceId = UUID.randomUUID().toString();

		String url = googleDriveService.uplaodFile(
				PictureUtil.convertBufferedImageToInputStream(resizeImage, imageFormat, resourceId), path,
				resourceId, imageFormat);

		Resource createResource = resourceService.createResource(resourceId, url);

		return createResource;
	}

	private BufferedImage resizeImage(BufferedImage image, PictureType type) throws IOException {
		if (type.equals(PictureType.ORIGIN)) {
			return image;
		}

		PictureSize size = getSize(type);

		if (image.getHeight() > image.getWidth()) {
			int height = size.getHeight();
			int width = size.getWidth();

			size = new PictureSize(0, 0);
			size.setHeight(width);
			size.setWidth(height);
		}

		return Thumbnails.of(image).size(size.getWidth(), size.getHeight()).asBufferedImage();

	}

	private PictureSize getSize(PictureType type) {

		switch (type) {
		case THUMBNAIL:
			return PictureSize.builder().height(thumbnailPicutreSizeHeight).width(thumbnailPicutreSizeWidth).build();
		case FULLSCREEN:
			return PictureSize.builder().height(fullScreenSizeHeight).width(fullScreenSizeWidth).build();
		default:
			return null;
		}
	}

	@Override
	public Picture getPictureById(String pictureId) {

		Picture picture = pictureRepository.findOne(pictureId);
		if (picture == null) {
			throw new ApiException("Picture not found");
		}

		return picture;
	}

}
