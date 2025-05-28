package com.oauth2proj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oauth2proj.utils.security.RsaTools; 

@SpringBootApplication
public class Oauth2projApplication {

	public static void main(String[] args) {
        Logger log = LogManager.getLogger(Oauth2projApplication.class);

		try {
			RsaTools rsaTools = new RsaTools();
			rsaTools.generateKeyPair();
		} catch (Exception e) {
            log.error("Error generating RSA key pair on application startup", e);
		}
		
		SpringApplication.run(Oauth2projApplication.class, args);
	}

}
