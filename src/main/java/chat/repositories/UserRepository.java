package chat.repositories;

import chat.models.entities.UserEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<UserEntity, Long> {
    void save(final UserEntity user);
    Optional<UserEntity> findByUsername(final String username);
    boolean existsByUsername(final String username);
}
