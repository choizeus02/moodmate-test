package moodmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApiApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:application-aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
