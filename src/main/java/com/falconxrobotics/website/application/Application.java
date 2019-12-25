package com.falconxrobotics.website.application;

import java.io.IOException;
// import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		// For testing:
		// SpringApplication app = new SpringApplication(Application.class);
		// app.setDefaultProperties(Collections
        //   .singletonMap("server.port", "80"));
		// app.run(args);
		
		// For release:
		SpringApplication.run(Application.class, args);
	}

}