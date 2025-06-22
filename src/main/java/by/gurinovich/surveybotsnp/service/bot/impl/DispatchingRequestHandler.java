package by.gurinovich.surveybotsnp.service.bot.impl;

import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.bot.MessageType;
import by.gurinovich.surveybotsnp.model.bot.RequestContext;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;
import by.gurinovich.surveybotsnp.service.bot.RequestHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DispatchingRequestHandler implements RequestHandler {

    private final Map<MessageType, RequestHandler> handlers;

    @Override
    public ResponsePayload handle(final RequestContext context) {
        var handler = handlers.get(context.messageType());

        if (handler == null) {
            throw SurveyBotDomainLogicException.internalServerError(
                    "Request bot not found for message type: " + context.messageType().name(),
                    context.chatId()
            );
        }

        return handler.handle(context);
    }
}
