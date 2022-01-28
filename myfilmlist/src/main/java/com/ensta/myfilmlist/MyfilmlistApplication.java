package com.ensta.myfilmlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Lancement de l'application Spring Boot et du serveur d'application.
 */
@SpringBootApplication

public class MyfilmlistApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyfilmlistApplication.class, args);
	}

}
