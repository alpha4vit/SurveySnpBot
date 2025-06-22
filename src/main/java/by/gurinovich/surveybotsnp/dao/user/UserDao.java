package by.gurinovich.surveybotsnp.dao.user;

import by.gurinovich.surveybotsnp.model.user.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByChatId(Long chatId);

    User save(User user);
}
