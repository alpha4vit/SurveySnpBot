package by.gurinovich.surveybotsnp.service.survey.impl;

import by.gurinovich.surveybotsnp.model.survey.Survey;
import by.gurinovich.surveybotsnp.model.user.User;
import by.gurinovich.surveybotsnp.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CachedSurveyService implements SurveyService {

    private final SurveyService delegate;

    private final Map<Long, Survey> surveyByChatId = new ConcurrentHashMap<>();

    @Override
    public Survey createOrResetActive(final User user) {
        var survey = delegate.createOrResetActive(user);

        surveyByChatId.put(user.getChatId(), survey);

        return survey;
    }

    @Override
    public Survey findActive(final User user) {
        return surveyByChatId.computeIfAbsent(user.getChatId(), (key) -> delegate.findActive(user));
    }

    @Override
    public Survey update(final Survey survey) {
        var updated = delegate.update(survey);

        surveyByChatId.put(survey.getUser().getChatId(), survey);

        return updated;
    }

    @Override
    public List<Survey> findAllCompleted(final Long chatId) {
        return delegate.findAllCompleted(chatId);
    }
}
