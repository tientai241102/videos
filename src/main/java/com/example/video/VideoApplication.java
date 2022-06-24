package com.example.video;

import com.example.video.configuration.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@EnableConfigurationProperties(FileStorageProperties.class)
@SpringBootApplication
@EntityScan(basePackageClasses = {VideoApplication.class, Jsr310JpaConverters.class})
public class VideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}

}
