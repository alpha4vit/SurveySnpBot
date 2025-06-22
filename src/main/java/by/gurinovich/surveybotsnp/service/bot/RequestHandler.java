package by.gurinovich.surveybotsnp.service.bot;

import by.gurinovich.surveybotsnp.model.bot.RequestContext;
import by.gurinovich.surveybotsnp.model.bot.ResponsePayload;

public interface RequestHandler {

    ResponsePayload handle(RequestContext context);

}
