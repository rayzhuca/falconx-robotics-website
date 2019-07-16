package com.falconxrobotics.website;

import java.io.IOException;
import java.util.Arrays;

import com.falconxrobotics.website.googlesheets.GoogleSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

}