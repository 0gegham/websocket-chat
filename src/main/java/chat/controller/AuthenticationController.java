package chat.controller;

import chat.mapper.UserMapper;
import chat.models.TokenResponse;
import chat.models.dto.UserDTO;
import chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(path = "/register", consumes = APPLICATION_JSON)
    @ResponseStatus(CREATED)
    public String register(@RequestBody UserDTO user) {
        return userService.save(userMapper.userDtoToUserEntity(user));
    }

    @PostMapping(path = "/login")
    public TokenResponse login(@RequestBody UserDTO user) {
        return userService.login(userMapper.userDtoToUserEntity(user));
    }

    @PostMapping(path = "/refreshToken")
    public TokenResponse refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
