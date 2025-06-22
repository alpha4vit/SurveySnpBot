package by.gurinovich.surveybotsnp.bot;

import by.gurinovich.surveybotsnp.config.SurveyBotConfig;
import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.bot.MessageType;
import by.gurinovich.surveybotsnp.model.bot.RequestContext;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import by.gurinovich.surveybotsnp.service.bot.DirectSender;
import by.gurinovich.surveybotsnp.service.bot.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SurveyBot extends TelegramLongPollingBot {

    private final SurveyBotConfig botConfig;
    private final RequestHandler requestHandler;
    private final Map<ResponseType, DirectSender> responseSenders;

    @SneakyThrows
    @Override
    public void onUpdateReceived(final Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var context = mapToRequestContext(update.getMessage());

            try {
                var response = requestHandler.handle(context);

                getSender(context.chatId(), response.type()).send(response);
            } catch (Throwable t) {
                getSender(context.chatId(), ResponseType.TEXT)
                        .send(
                                new ResponsePayload(
                                        context.chatId(),
                                        t.getMessage(),
                                        ResponseType.TEXT
                                )
                        );
            }
        }
    }

    @Override
    public void onUpdatesReceived(final List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    private DirectSender getSender(Long chatId, ResponseType type) {
        var sender = responseSenders.get(type);

        if (sender == null) {
            throw SurveyBotDomainLogicException.internalServerError(
                    "Message sender not found for response type " + type.name(),
                    chatId
            );
        }

        return sender;
    }

    private RequestContext mapToRequestContext(final Message message) {
        return new RequestContext(
                message.getChatId(),
                MessageType.MESSAGE,
                message.getText()
        );
    }

}
