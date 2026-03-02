package com.smwu_itple.backend;

import com.smwu_itple.backend.pay.dto.KakaoPayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SmwuItpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmwuItpleApplication.class, args);
	}

}
