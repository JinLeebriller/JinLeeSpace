package com.blog.JinLeeSpace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@SpringBootApplication
public class JinLeeSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinLeeSpaceApplication.class, args);
	}

}
