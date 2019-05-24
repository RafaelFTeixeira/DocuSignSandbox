package com.docusign.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BpApplication {

	public static void main(String[] args) {
		Configuration.ACCOUNT_ID = System.getenv("ACCOUNT_ID");
		Configuration.ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
		SpringApplication.run(BpApplication.class, args);
	}

}
