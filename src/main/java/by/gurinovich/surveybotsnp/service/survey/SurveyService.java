package by.gurinovich.surveybotsnp.service.survey;

import by.gurinovich.surveybotsnp.model.user.User;
import by.gurinovich.surveybotsnp.model.survey.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyService {
    Survey createOrResetActive(User user);

    Optional<Survey> findActive(User user);

    Survey update(Survey survey);

    List<Survey> findAllCompleted(Long chatId);
}
