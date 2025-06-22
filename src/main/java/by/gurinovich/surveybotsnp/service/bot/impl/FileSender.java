package by.gurinovich.surveybotsnp.service.bot.impl;

import by.gurinovich.surveybotsnp.config.SurveyBotConfig;
import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import by.gurinovich.surveybotsnp.service.bot.DirectSender;
import by.gurinovich.surveybotsnp.utils.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class FileSender extends DefaultAbsSender implements DirectSender {

    protected FileSender(DefaultBotOptions botOptions, SurveyBotConfig botConfig) {
        super(botOptions, botConfig.token);
    }

    @Override
    public ResponseType messageType() {
        return ResponseType.FILE;
    }

    @Override
    public void send(final ResponsePayload payload) throws TelegramApiException {
        if (!(payload.data() instanceof InputFile)) {
            log.error("File sender cant send value of type '{}'", payload.data().getClass());

            throw SurveyBotDomainLogicException.internalServerError(
                    MessageConstants.INTERNAL_SERVER_ERROR, payload.chatId()
            );
        }

        var documentResponse = new SendDocument(payload.chatId().toString(), (InputFile) payload.data());

        execute(documentResponse);
    }
}
