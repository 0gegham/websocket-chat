package chat.service;

import chat.models.RefreshTokenRequest;
import chat.models.TokenResponse;
import chat.models.entities.UserEntity;

public interface UserService {
    String save(final UserEntity user);
    TokenResponse login(final UserEntity userEntity);
    TokenResponse refreshToken(final RefreshTokenRequest refreshTokenRequest);
    UserEntity getByUsername(final String username);
}
