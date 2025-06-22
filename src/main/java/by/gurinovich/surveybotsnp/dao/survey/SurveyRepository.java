package by.gurinovich.surveybotsnp.dao.survey;

import by.gurinovich.surveybotsnp.model.survey.Survey;
import by.gurinovich.surveybotsnp.model.survey.SurveyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SurveyRepository extends SurveyDao, JpaRepository<Survey, UUID> {

    @Override
    @Query("select s from Survey s where s.user.chatId = :chatId and s.state in :states")
    Optional<Survey> findByStatesAndChatId(@Param("chatId") Long chatId, @Param("states") Set<SurveyState> state);

    @Query("select s from Survey s where s.user.chatId = :chatId and s.state = :state")
    List<Survey> findAllByChatIdAndState(@Param("chatId") Long chatId, @Param("state") SurveyState state);

}
