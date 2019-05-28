package com.docusign.bp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BpApplication {

	public static void main(String[] args) {
		Configuration.ACCOUNT_ID = System.getenv("ACCOUNT_ID");
		Configuration.ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
		Configuration.TEMPLATE_ID = System.getenv("TEMPLATE_ID");
		System.out.println(Configuration.ACCESS_TOKEN);
		System.out.println(Configuration.ACCOUNT_ID);
		SpringApplication.run(BpApplication.class, args);
	}
}
