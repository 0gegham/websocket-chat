package chat.controller;

import chat.mapper.UserMapper;
import chat.models.RefreshTokenRequest;
import chat.models.TokenResponse;
import chat.models.dto.UserDto;
import chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(path = "/register")
    @ResponseStatus(CREATED)
    public String register(@RequestBody final UserDto user) {
        return userService.save(userMapper.userDtoToUserEntity(user));
    }

    @PostMapping(path = "/login")
    public TokenResponse login(@RequestBody final UserDto user) {
        return userService.login(userMapper.userDtoToUserEntity(user));
    }

    @PostMapping(path = "/refresh_token")
    public TokenResponse refreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }
}
