package by.gurinovich.surveybotsnp.service.user.impl;

import by.gurinovich.surveybotsnp.dao.user.UserDao;
import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.model.user.User;
import by.gurinovich.surveybotsnp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional
    public User save(final Long chatId) {
        return userDao.findByChatId(chatId)
                .orElseGet(() ->
                        {
                            var user = new User(chatId);

                            return userDao.save(user);
                        }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public User getByChatId(Long chatId) {
        return userDao.findByChatId(chatId)
                .orElseThrow(
                        () -> SurveyBotDomainLogicException.userIsNotRegisteredInSystem(chatId)
                );
    }
}
