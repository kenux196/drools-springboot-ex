package study.example.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroolsApplication.class, args);
	}

}
