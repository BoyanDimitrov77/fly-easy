package com.fly.easy.flyeasy.service.interfaces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface GoogleDriveService {

	String uplaodFile(InputStream inputStream, String usernameFolder, String fileId , String fileType) throws IOException;
}
