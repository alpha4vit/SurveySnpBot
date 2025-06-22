package by.gurinovich.surveybotsnp.model.bot;

public record ResponsePayload(
        Long chatId,
        Object data,
        ResponseType type
) {
}
