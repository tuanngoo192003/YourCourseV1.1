package com.project.CourseSystem.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.project.CourseSystem.service.GoogleDriveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveServiceImpl.class);

    @Value("${google.service_account_email}")
    private String serviceAccountEmail;
    @Value("${google.application_name}")
    private String applicationName;
    @Value("${google.service_account_key}")
    private String serviceAccountKey;
    @Value("${google.folder_id_userAvatar}")
    private String folderIDUserAvatar;
    @Value("${google.folder_id_courseAvatar}")
    private String folderIDCourseAvatar;
    @Value("${google.folder_id_learningMaterial}")
    private String folderIDLearningMaterial;

    public Drive getDriveService(){
        Drive service = null;
        try{
            Resource resource = new ClassPathResource("key/"+this.serviceAccountKey);
            java.io.File key = resource.getFile();
            HttpTransport httpTransport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();

            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(this.serviceAccountEmail)
                    .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
                    .setServiceAccountPrivateKeyFromP12File(key)
                    .build();
            service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(applicationName)
                    .setHttpRequestInitializer(credential).build();
        }
        catch (Exception e){
            LOGGER.error("Error getDriveService: ", e);
        }
        return service;
    }

    @Override
    public File uploadFile(String fileName, String filePath, String mimeType, String folder) {
        File file = new File();
        try{
            java.io.File fileUpload = new java.io.File(filePath);
            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            fileMetadata.setMimeType(mimeType);
            if(folder.equals("UserAvatar")){
                fileMetadata.setParents(Collections.singletonList(folderIDUserAvatar));
            }
            else if(folder.equals("CourseAvatar")){
                fileMetadata.setParents(Collections.singletonList(folderIDCourseAvatar));
            }
            else{
                fileMetadata.setParents(Collections.singletonList(folderIDLearningMaterial));
            }
            com.google.api.client.http.FileContent fileContent = new com.google.api.client.http.FileContent(mimeType, fileUpload);
            file = getDriveService().files().create(fileMetadata, fileContent).setFields("id, webContentLink, webViewLink").execute();
        }
        catch (Exception e){
            LOGGER.error("Error uploadFile: ", e);
        }
        return file;
    }
}
