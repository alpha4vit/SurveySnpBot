package by.gurinovich.surveybotsnp.model.bot;

public record RequestContext(
        Long chatId,
        MessageType messageType,
        String payload
) {
}