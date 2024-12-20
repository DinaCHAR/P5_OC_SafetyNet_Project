package com.openclassroom.SafetyNet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.openclassroom.api")
public class SafetyNetApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}
	
	 @Override
	    public void run(String... args) throws Exception {
	        System.out.println("Hello World!");
	    }
}


