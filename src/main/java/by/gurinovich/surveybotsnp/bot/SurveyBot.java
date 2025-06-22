package by.gurinovich.surveybotsnp.bot;

import by.gurinovich.surveybotsnp.config.SurveyBotConfig;
import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.bot.MessageType;
import by.gurinovich.surveybotsnp.model.bot.RequestContext;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.model.bot.ResponseType;
import by.gurinovich.surveybotsnp.service.bot.DirectSender;
import by.gurinovich.surveybotsnp.service.bot.RequestHandler;
import by.gurinovich.surveybotsnp.service.bot.impl.AsyncRunner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SurveyBot extends TelegramLongPollingBot {

    private final SurveyBotConfig botConfig;
    private final RequestHandler requestHandler;
    private final Map<ResponseType, DirectSender> responseSenders;
    private final AsyncRunner asyncRunner;

    @Override
    @SneakyThrows
    public void onUpdateReceived(final Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var context = mapToRequestContext(update.getMessage());

            asyncRunner.runAsync(() -> {
                        try {
                            var response = requestHandler.handle(context);

                            getSender(context.chatId(), response.type()).send(response);
                        } catch (Throwable t) {
                            sendErrorMessage(context.chatId(), t.getMessage());
                        }
                    }
            );
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

    private void sendErrorMessage(Long chatId, String message) {
        try {
            getSender(chatId, ResponseType.TEXT)
                    .send(
                            new ResponsePayload(
                                    chatId,
                                    message,
                                    ResponseType.TEXT
                            )
                    );

        } catch (Throwable t) {
            log.error(t.getMessage());
        }
    }

}
