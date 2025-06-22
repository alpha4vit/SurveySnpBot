package by.gurinovich.surveybotsnp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bot")
public class SurveyBotConfig {
    public String name;
    public String token;
}
