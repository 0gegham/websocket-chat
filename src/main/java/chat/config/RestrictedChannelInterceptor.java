package chat.config;

import chat.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestrictedChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    private static final EnumSet<StompCommand> RESTRICTED_COMMANDS = EnumSet.of(
            StompCommand.CONNECT,
            StompCommand.SEND
    );

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (isRestrictedCommand(accessor)) {

            final String token = ofNullable(accessor).flatMap(this::getToken)
                    .orElseThrow(() -> new BadCredentialsException("Please provide"));
            log.debug("X-Authorization: {}", token);

            if (jwtProvider.validateToken(token)) {
                final String username = jwtProvider.getBody(token).getSubject();
                final UserDetails user = userDetailsService.loadUserByUsername(username);

                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword(), user.getAuthorities());

                accessor.setUser(auth);
            } else {
                throw new BadCredentialsException("Please authenticate");
            }
        }

        return message;
    }

    private boolean isRestrictedCommand(final StompHeaderAccessor accessor) {
        return nonNull(accessor)
                && nonNull(accessor.getCommand())
                && RESTRICTED_COMMANDS.contains(accessor.getCommand());
    }

    private Optional<String> getToken(final StompHeaderAccessor accessor) {
        final List<String> authorizationValues = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);

        if (!CollectionUtils.isEmpty(authorizationValues)) {
            return authorizationValues.stream().findFirst()
                    .map(authorization -> authorization.replaceFirst("Bearer", "").trim())
                    .filter(StringUtils::hasLength);
        }

        return Optional.empty();
    }
}
