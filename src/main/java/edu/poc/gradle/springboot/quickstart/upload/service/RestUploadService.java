package edu.poc.gradle.springboot.quickstart.upload.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RestUploadService {

    private final Logger logger = LoggerFactory.getLogger(RestUploadService.class);

    /**
     * Save file into store-path location
     * 
     * @param files
     * @param storePath
     * @throws IOException
     */
    public void saveUploadedFiles(List<MultipartFile> files, Path rootLocation) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; // Go for next file
            }
            
            // Get store path
            Path storePath = rootLocation.resolve(file.getOriginalFilename());
            
            // Check if file already exists in current location
            File dir = new File(storePath.toString());
            if (dir.exists()) {
            	continue; // Go for next file
            }
            
            // Copy byte stream of uploaded file into store path
            Files.copy(file.getInputStream(), storePath);

            /*
            Note: Alternate way to store file.
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            */
            
            logger.debug("Successfully uploaded - " + file.getOriginalFilename());
        }
    }
}