package com.fly.easy.flyeasy.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fly.easy.flyeasy.service.interfaces.GoogleDriveService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

	@Value("${download.link.file.google.drive}")
	private String downloadUrl;

	/** Application name. */
	private static final String APPLICATION_NAME = "Fly Easy";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/fly-easy");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	private Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = GoogleDriveServiceImpl.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Drive client service.
	 * 
	 * @return an authorized Drive client service
	 * @throws IOException
	 */
	private Drive getDriveService() throws IOException {
		Credential credential = authorize();
		System.out.println(credential.getAccessToken());
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

	}

	@Override
	public String uplaodFile(InputStream inputStream, String path, String fileId, String fileType) throws IOException {

		String folderId = createFolder("FlyEasy", path);

		String uploadFileId = insertFileInFolder(folderId, inputStream, fileId, fileType);

		return downloadUrl + uploadFileId + "?alt=media";
	}

	private String createFolder(String mainfolderName, String subFolderName) throws IOException {

		Optional<com.google.api.services.drive.model.File> mainFolder = findFolderInDrive(mainfolderName);

		String mainFolderID = null;

		if (!mainFolder.isPresent()) {
			mainFolderID = createMainFolder("FlyEasy");
		}else{
			mainFolderID = mainFolder.get().getId();
		}
		
		Optional<com.google.api.services.drive.model.File> subFolder = findFolderInDrive(subFolderName);
		String subFolderId= null;
		if(!subFolder.isPresent()){
			subFolderId = createSubFolder(mainFolderID, subFolderName);
		}else{
			subFolderId = subFolder.get().getId();
		}

		return subFolderId;

	}

	private String createMainFolder(String folderName) throws IOException {

		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
		fileMetadata.setName(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		com.google.api.services.drive.model.File file = getDriveService().files().create(fileMetadata).setFields("id")
				.execute();

		return file.getId();

	}

	private Optional<com.google.api.services.drive.model.File> findFolderInDrive(String searchNameFolder)
			throws IOException {

		FileList resultQuery = getDriveService().files().list().setQ("mimeType='application/vnd.google-apps.folder'")
				.setFields("files(id,name)").execute();

		return resultQuery.getFiles().stream().filter(file -> file.getName().equals(searchNameFolder)).findFirst();
	}

	private String createSubFolder(String mainFoolderId, String folderName) throws IOException {

		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
		fileMetadata.setName(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");
		fileMetadata.setParents(Collections.singletonList(mainFoolderId));

		com.google.api.services.drive.model.File file = getDriveService().files().create(fileMetadata).setFields("id")
				.execute();

		return file.getId();
	}

	private String insertFileInFolder(String folderId, InputStream inputStream, String fileId, String fileType) throws IOException {

		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
		fileMetadata.setName(fileId);
		fileMetadata.setParents(Collections.singletonList(folderId));
		//FileContent mediaContent = new FileContent("image/" + fileType, file);
		InputStreamContent mediaContent = new InputStreamContent("image/" + fileType,inputStream);
		

		com.google.api.services.drive.model.File newfile = getDriveService().files().create(fileMetadata, mediaContent)
				.setFields("id").execute();
		
		return newfile.getId();

	}
}
