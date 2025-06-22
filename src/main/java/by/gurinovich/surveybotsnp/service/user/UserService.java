package by.gurinovich.surveybotsnp.service.user;

import by.gurinovich.surveybotsnp.model.user.User;

public interface UserService {

    User save(
            Long chatId
    );

    User getByChatId(Long chatId);

}
