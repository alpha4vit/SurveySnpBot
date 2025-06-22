package by.gurinovich.surveybotsnp.service.validator;

import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;

public class ScoreValidator {

    public static void validate(Long chatId, String value) {
        if (value == null || value.isEmpty()) {
            throw SurveyBotDomainLogicException.invalidScoreInput(chatId);
        }

        try {
            var score = Long.parseLong(value);

            if (score < 1 || score > 10) {
                throw new IllegalArgumentException("Invalid");
            }

        } catch (Exception e) {
            throw SurveyBotDomainLogicException.invalidScoreInput(chatId);
        }
    }
}
