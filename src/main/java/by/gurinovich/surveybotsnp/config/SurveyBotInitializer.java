package by.gurinovich.surveybotsnp.config;

import by.gurinovich.surveybotsnp.bot.SurveyBot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class SurveyBotInitializer {

    private final SurveyBot surveyBot;

    @PostConstruct
    public void init() throws TelegramApiException {
        log.info("Starting registering survey bot");

        var botsApi = new TelegramBotsApi(DefaultBotSession.class);

        botsApi.registerBot(surveyBot);

        log.info("Survey bot successfully registered!");
    }
}