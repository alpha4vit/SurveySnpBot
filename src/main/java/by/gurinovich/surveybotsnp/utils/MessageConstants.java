package by.gurinovich.surveybotsnp.utils;

import by.gurinovich.surveybotsnp.model.bot.ActionType;

import java.util.Arrays;

public class MessageConstants {

    public static String HELLO_MESSAGE = """
            Добро пожаловать в бот-отпросник!
            Вот список доступных команд:
            /form - начать заполнение анкеты
            /report - получить отчет.
            Приятного использования!
            """;

    public static String SURVEY_NAME_WAITING_MESSAGE = "Введите ваше имя!";
    public static String SURVEY_EMAIL_WAITING_MESSAGE = "Введите вашу электронную почту!";
    public static String SURVEY_EMAIL_INVALID_ERROR = "Электронная почта введена неверно! Попробуйте снова";
    public static String SURVEY_SCORE_INVALID_ERROR =
            "Оценка введена неверно! Значение должно быть от 1 до 10. Попробуйте снова";
    public static String SURVEY_SCORE_WAITING_MESSAGE = "Введите вашу оценку!";
    public static String SURVEY_COMPLETED_MESSAGE = """
            Форма успешно сохранена!
            Спасибо за ваш отзыв!
                        
            Имя пользователя: %s
            Электронная почта: %s
            Оценка: %s
                        
            Для повторной отправки формы /form
            """;

    public static String NOT_HANDLED_COMMAND_MESSAGE = """
            Команда не зарегстрирована в системе.
            Список доступных команд:
            """ +
            String.join(
                    "/n",
                    Arrays.stream(ActionType.values())
                            .map(ActionType::getPath)
                            .toList());

    public static String USER_IS_NOT_REGISTERED =
            "Пользователь не зарегестрирован в системе. Попробуйте команду /start.";


    // internal errors
    public static String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера. Попробуйте снова!";
    public static String REPORT_GENERATING_ERROR = "Ошибка генерации отчета. Попробуйте позже!";

}
