package com.actec.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MsApplication {

	public static void main(String[] args) {
//		SpringApplication newRun= new SpringApplication(PaccipiterApplication.class);
//		newRun.setBannerMode(Banner.Mode.LOG);
//		newRun.run(args);
		SpringApplication.run(MsApplication.class, args);
	}
}
