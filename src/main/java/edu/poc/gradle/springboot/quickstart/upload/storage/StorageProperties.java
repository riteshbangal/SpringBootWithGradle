package edu.poc.gradle.springboot.quickstart.upload.storage;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/upload-configuration.properties")
@ConfigurationProperties("storage")
public class StorageProperties {

    // Folder location for storing files
	@NotEmpty
	private String location;
	
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
