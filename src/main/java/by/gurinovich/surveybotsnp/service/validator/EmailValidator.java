package by.gurinovich.surveybotsnp.service.validator;

import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void validate(Long chatId, String email) {
        var isValid = (email != null && EMAIL_PATTERN.matcher(email).matches());

        if (!isValid) {
            throw SurveyBotDomainLogicException.invalidEmailInput(chatId);
        }
    }
}
