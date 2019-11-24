package me.achyutdev.reprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ReprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReprocessorApplication.class, args);
	}

}
