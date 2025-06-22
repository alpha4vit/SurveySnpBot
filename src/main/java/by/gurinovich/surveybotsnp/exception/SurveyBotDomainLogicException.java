package by.gurinovich.surveybotsnp.exception;

import by.gurinovich.surveybotsnp.utils.MessageConstants;

public class SurveyBotDomainLogicException {

    public static TelegramBotLogicDomainException internalServerError(String message, Long chatId) {
        return new TelegramBotLogicDomainException(message, chatId);
    }

    public static TelegramBotLogicDomainException userIsNotRegisteredInSystem(Long chatId) {
        return new TelegramBotLogicDomainException(MessageConstants.USER_IS_NOT_REGISTERED, chatId);
    }

    public static TelegramBotLogicDomainException notHandledCommand(Long chatId) {
        return new TelegramBotLogicDomainException(MessageConstants.NOT_HANDLED_COMMAND_MESSAGE, chatId);
    }

    public static TelegramBotLogicDomainException invalidEmailInput(Long chatId) {
        return new TelegramBotLogicDomainException(MessageConstants.SURVEY_EMAIL_INVALID_ERROR, chatId);
    }

    public static TelegramBotLogicDomainException invalidScoreInput(Long chatId) {
        return new TelegramBotLogicDomainException(MessageConstants.SURVEY_SCORE_INVALID_ERROR, chatId);
    }

}
