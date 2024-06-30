package com.project.recommend;

import com.project.recommend.model.recommend;
import com.project.recommend.service.recommendImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class RecommendApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecommendApplication.class, args);

	}
	@Bean
	CommandLineRunner initDatabase(recommendImpl recommendImpl) {

		return args -> {
			recommend recommendation = new recommend(1L, "kevin", 9L, "Was great");
			recommendImpl.createRecommendation(recommendation);
			recommend recommendation2 = new recommend(1L, "jane", 9L, "Was great");
			recommendImpl.createRecommendation(recommendation2);
			recommend recommendation3 = new recommend(2L, "smith", 9L, "Was great");
			recommendImpl.createRecommendation(recommendation3);

		};
	}

}
