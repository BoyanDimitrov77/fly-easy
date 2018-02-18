package com.fly.easy.flyeasy.util;

import java.awt.image.BufferedImage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PictureInfo {

	private BufferedImage image;

	private String type;
}
