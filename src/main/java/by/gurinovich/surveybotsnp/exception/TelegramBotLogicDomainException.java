package by.gurinovich.surveybotsnp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TelegramBotLogicDomainException extends RuntimeException {

    private Long chatId;

    public TelegramBotLogicDomainException(String message, Long chatId) {
        super(message);

        this.chatId = chatId;
    }
}

