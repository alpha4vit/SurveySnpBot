package by.gurinovich.surveybotsnp.dao.survey;

import by.gurinovich.surveybotsnp.model.survey.Survey;
import by.gurinovich.surveybotsnp.model.survey.SurveyState;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SurveyDao {

    Survey save(Survey survey);

    Optional<Survey> findByStatesAndChatId(Long chatId, Set<SurveyState> state);

    List<Survey> findAllByChatIdAndState(Long chatId, SurveyState state);

}
