package chat.service.impl;

import chat.config.jwt.JwtProvider;
import chat.exception.WrongUserException;
import chat.models.RefreshTokenRequest;
import chat.models.TokenResponse;
import chat.models.entities.UserEntity;
import chat.repositories.UserRepository;
import chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String save(final UserEntity user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new WrongUserException("Username already in use", HttpStatus.BAD_REQUEST);
        }

        final String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return "ok";
    }

    @Override
    public TokenResponse login(final UserEntity userEntity) {
        final String username = userEntity.getUsername();
        final String password = userEntity.getPassword();

        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WrongUserException("Invalid username/password", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongUserException("Invalid username/password", HttpStatus.NOT_FOUND);
        }

        return createTokenResponse(username);
    }

    @Override
    public TokenResponse refreshToken(final RefreshTokenRequest refreshTokenRequest) {
        String username = refreshTokenRequest.getUsername();

        if (!userRepository.existsByUsername(username)) {
            throw new WrongUserException("User not found", HttpStatus.NOT_FOUND);
        }

        return createTokenResponse(username);
    }

    @Override
    public UserEntity getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new WrongUserException("User not found", HttpStatus.NOT_FOUND));
    }

    private TokenResponse createTokenResponse(final String username) {
        final String token = jwtProvider.generateToken(username);
        final long exp = jwtProvider.getExpirationDateTime(token);
        return new TokenResponse(username, token, exp);
    }

}
