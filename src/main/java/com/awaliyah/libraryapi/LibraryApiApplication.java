package com.awaliyah.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LibraryApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibraryApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfig() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "PUT", "OPTION","POST", "DELETE")
						.maxAge(900)
						.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization");
			}
		};
	}

}
