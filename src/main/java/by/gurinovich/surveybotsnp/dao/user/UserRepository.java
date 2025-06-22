package by.gurinovich.surveybotsnp.dao.user;

import by.gurinovich.surveybotsnp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends UserDao, JpaRepository<User, Long> {
    @Override
    Optional<User> findByChatId(Long chatId);
}
