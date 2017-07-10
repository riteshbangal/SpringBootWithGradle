package edu.poc.gradle.springboot.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import edu.poc.gradle.springboot.quickstart.upload.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootWithGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithGradleApplication.class, args);
	}
}
