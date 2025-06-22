package by.gurinovich.surveybotsnp.service.bot;

import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface DirectSender {

    ResponseType messageType();

    void send(ResponsePayload payload) throws TelegramApiException;
}
