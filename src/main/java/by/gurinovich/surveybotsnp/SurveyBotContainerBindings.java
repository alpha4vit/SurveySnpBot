package by.gurinovich.surveybotsnp;

import by.gurinovich.surveybotsnp.dao.user.UserDao;
import by.gurinovich.surveybotsnp.model.bot.MessageType;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import by.gurinovich.surveybotsnp.service.bot.DirectSender;
import by.gurinovich.surveybotsnp.service.bot.RequestHandler;
import by.gurinovich.surveybotsnp.service.bot.impl.DispatchingRequestHandler;
import by.gurinovich.surveybotsnp.service.bot.impl.FileSender;
import by.gurinovich.surveybotsnp.service.bot.impl.MessageRequestHandler;
import by.gurinovich.surveybotsnp.service.bot.impl.MessageSender;
import by.gurinovich.surveybotsnp.service.report.ReportService;
import by.gurinovich.surveybotsnp.service.survey.SurveyService;
import by.gurinovich.surveybotsnp.service.user.UserService;
import by.gurinovich.surveybotsnp.service.user.impl.CachedUserService;
import by.gurinovich.surveybotsnp.service.user.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.Map;

@Configuration
public class SurveyBotContainerBindings {

    private RequestHandler messageRequestHandler(UserService userService,
                                                 SurveyService surveyService,
                                                 ReportService reportService
    ) {
        return new MessageRequestHandler(
                userService, surveyService, reportService
        );
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public RequestHandler dispatchingRequestHandler(UserService userService,
                                                    SurveyService surveyService,
                                                    ReportService reportService) {
        return new DispatchingRequestHandler(
                Map.of(
                        MessageType.MESSAGE, messageRequestHandler(userService, surveyService, reportService)
                )
        );
    }

    @Bean
    public UserService userDao(UserDao userDao) {
        return new CachedUserService(
                new UserServiceImpl(userDao)
        );
    }

    @Bean
    public Map<ResponseType, DirectSender> responseSenders(
            FileSender fileSender, MessageSender messageSender
    ) {
        return Map.of(
                fileSender.messageType(), fileSender,
                messageSender.messageType(), messageSender
        );
    }

}
