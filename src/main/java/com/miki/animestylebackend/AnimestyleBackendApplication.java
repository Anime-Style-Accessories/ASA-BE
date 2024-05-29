package com.miki.animestylebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnimestyleBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimestyleBackendApplication.class, args);
	}

}
