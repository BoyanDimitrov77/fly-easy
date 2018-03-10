package com.fly.easy.flyeasy.service.interfaces;

import java.io.IOException;

import com.fly.easy.flyeasy.api.dto.PictureDto;
import com.fly.easy.flyeasy.db.model.Picture;
import com.fly.easy.flyeasy.util.PictureInfo;

public interface PictureService {

	PictureDto savePicure(PictureInfo image , String path) throws IOException;

	Picture getPictureById(String pictureId);
}
