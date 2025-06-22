package by.gurinovich.surveybotsnp.service.user.impl;

import by.gurinovich.surveybotsnp.model.user.User;
import by.gurinovich.surveybotsnp.service.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CachedUserService implements UserService {

    private final UserService delegate;

    private final Map<Long, User> userByChatId = new ConcurrentHashMap<>();

    @Override
    public User save(final Long chatId) {
        return userByChatId.computeIfAbsent(chatId, (key) -> delegate.save(chatId));
    }

    @Override
    public User getByChatId(final Long chatId) {
        return userByChatId.computeIfAbsent(chatId, delegate::getByChatId);
    }
}
