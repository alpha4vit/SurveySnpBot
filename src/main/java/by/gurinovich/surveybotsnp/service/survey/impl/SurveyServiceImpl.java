package by.gurinovich.surveybotsnp.service.survey.impl;

import by.gurinovich.surveybotsnp.dao.survey.SurveyDao;
import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.survey.Survey;
import by.gurinovich.surveybotsnp.model.survey.SurveyState;
import by.gurinovich.surveybotsnp.model.user.User;
import by.gurinovich.surveybotsnp.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static by.gurinovich.surveybotsnp.model.survey.SurveyState.*;

@Transactional
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyDao surveyDao;

    private static final Set<SurveyState> activeStates = Set.of(NAME, EMAIL, SCORE);

    @Override
    public Survey createOrResetActive(final User user) {
        var existed = surveyDao.findByStatesAndChatId(user.getChatId(), activeStates);

        if (existed.isEmpty()) {
            var survey = new Survey(user, SurveyState.NAME);

            return surveyDao.save(survey);
        }

        var reset = existed.get().reset();

        return surveyDao.save(reset);
    }

    @Override
    @Transactional(readOnly = true)
    public Survey findActive(final User user) {
        return surveyDao.findByStatesAndChatId(user.getChatId(), activeStates).orElseThrow(
                () -> SurveyBotDomainLogicException.notHandledCommand(user.getChatId())
        );

    }

    @Override
    public Survey update(final Survey survey) {
        return surveyDao.save(survey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Survey> findAllCompleted(final Long chatId) {
        return surveyDao.findAllByChatIdAndState(chatId, COMPLETED);
    }
}
