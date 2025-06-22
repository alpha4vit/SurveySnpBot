package by.gurinovich.surveybotsnp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SurveyBotSnpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyBotSnpApplication.class, args);
    }

}
