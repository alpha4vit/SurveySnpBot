package by.gurinovich.surveybotsnp.service.bot.impl;

import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.bot.ActionType;
import by.gurinovich.surveybotsnp.model.bot.RequestContext;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import by.gurinovich.surveybotsnp.model.survey.SurveyState;
import by.gurinovich.surveybotsnp.service.bot.RequestHandler;
import by.gurinovich.surveybotsnp.service.report.ReportService;
import by.gurinovich.surveybotsnp.service.survey.SurveyService;
import by.gurinovich.surveybotsnp.service.user.UserService;
import by.gurinovich.surveybotsnp.service.validator.EmailValidator;
import by.gurinovich.surveybotsnp.service.validator.ScoreValidator;
import by.gurinovich.surveybotsnp.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.IOException;

@RequiredArgsConstructor
public class MessageRequestHandler implements RequestHandler {

    private final UserService userService;
    private final SurveyService surveyService;
    private final ReportService reportService;

    @Override
    public ResponsePayload handle(final RequestContext context) {
        var action = ActionType.getByPath(context.payload());

        return action.map(actionType ->
                        switch (actionType) {
                            case START -> handleStartAction(context.chatId());
                            case SURVEY -> handleStartSurveyAction(context.chatId());
                            case REPORT -> handleReport(context.chatId());
                        })
                .orElseGet(() -> handleMessage(context));
    }

    private ResponsePayload handleStartAction(Long chatId) {
        userService.save(chatId);

        return new ResponsePayload(chatId, MessageConstants.HELLO_MESSAGE, ResponseType.TEXT);
    }

    private ResponsePayload handleStartSurveyAction(Long chatId) {
        var user = userService.getByChatId(chatId);

        surveyService.createOrResetActive(user);

        return new ResponsePayload(chatId, MessageConstants.SURVEY_NAME_WAITING_MESSAGE, ResponseType.TEXT);
    }

    private ResponsePayload handleMessage(RequestContext context) {
        var user = userService.getByChatId(context.chatId());

        var survey = surveyService.findActive(user).orElseThrow(
                () -> SurveyBotDomainLogicException.notHandledCommand(context.chatId())
        );

        var message = switch (survey.state) {
            case NAME -> {
                survey.setName(context.payload());
                survey.setState(SurveyState.EMAIL);

                yield MessageConstants.SURVEY_EMAIL_WAITING_MESSAGE;
            }
            case EMAIL -> {
                EmailValidator.validate(context.chatId(), context.payload());

                survey.setEmail(context.payload());
                survey.setState(SurveyState.SCORE);

                yield MessageConstants.SURVEY_SCORE_WAITING_MESSAGE;
            }
            case SCORE -> {
                ScoreValidator.validate(context.chatId(), context.payload());

                var score = Long.parseLong(context.payload());

                survey.setScore(score);
                survey.setState(SurveyState.COMPLETED);

                yield MessageConstants.SURVEY_COMPLETED_MESSAGE.formatted(
                        survey.name,
                        survey.email,
                        survey.score

                );
            }
            case COMPLETED -> MessageConstants.SURVEY_COMPLETED_MESSAGE.formatted(
                    survey.name,
                    survey.email,
                    survey.score
            );
        };

        surveyService.update(survey);

        return new ResponsePayload(context.chatId(), message, ResponseType.TEXT);
    }

    private ResponsePayload handleReport(Long chatId) {
        var report = reportService.generate(chatId);

        try (var inputStream = report.getInputStream()) {
            return new ResponsePayload(chatId, new InputFile(inputStream, "report.docx"), ResponseType.FILE);
        } catch (IOException ex) {
            throw SurveyBotDomainLogicException.internalServerError(MessageConstants.REPORT_GENERATING_ERROR, chatId);
        }
    }
}
