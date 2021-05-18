package chat.service;

import chat.models.TokenResponse;
import chat.models.entities.UserEntity;

public interface UserService {
    String save(final UserEntity user);
    TokenResponse login(final UserEntity userEntity);
    TokenResponse refreshToken(final String username);
    UserEntity getByUsername(final String username);
}
