package edu.poc.gradle.springboot.quickstart.upload.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.poc.gradle.springboot.quickstart.upload.modal.UploadModel;
import edu.poc.gradle.springboot.quickstart.upload.service.RestUploadService;
import edu.poc.gradle.springboot.quickstart.upload.storage.StorageProperties;

@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    @Autowired
    private RestUploadService restUploadService;
    
    private final Path rootLocation;

    @Autowired
    public RestUploadController(StorageProperties properties) {
    	//Save the uploaded file to this folder
        this.rootLocation = Paths.get(properties.getLocation());
    }
    
    /**
     * 1. Single file upload
     * 
     * @param file
     * @return response entity
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/upload-api")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

		logger.debug("Single file upload!");

		if (file.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}

		try {
			restUploadService.saveUploadedFiles(Arrays.asList(file), rootLocation);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Successfully uploaded - " + file.getOriginalFilename(), 
				new HttpHeaders(),	HttpStatus.OK);
	}

    /**
     * 2. Multiple file upload
     * 
     * @param extraField
     * @param uploadfiles
     * @return response entity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/upload-api/multi")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        logger.debug("Multiple file upload!");

        // Get file name
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(", "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            restUploadService.saveUploadedFiles(Arrays.asList(uploadfiles), rootLocation);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }

    /**
     * 3. Maps HTML form to a Model
     * 
     * @param model
     * @return response entity
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/upload-api/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

        logger.debug("Multiple file upload! With UploadModel");

        try {
        	restUploadService.saveUploadedFiles(Arrays.asList(model.getFiles()), rootLocation);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    }
}
