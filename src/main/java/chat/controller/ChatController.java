package chat.controller;

import chat.mapper.MessageMapper;
import chat.models.dto.MessageDto;
import chat.models.entities.Message;
import chat.models.entities.UserEntity;
import chat.service.MessageService;
import chat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper messageMapper;

    @MessageMapping("/message.send")
    @SendTo("/topic/messages")
    public MessageDto save(@Payload final MessageDto message, final Principal principal) {
        log.info("Received a message");

        if (!StringUtils.hasLength(message.getContent())) {
            throw new InvalidParameterException("Please provide the message content");
        }

        Message messageEntity = messageMapper.messageDtoToMessageEntity(message);
        messageEntity.setOwner(getOwner(principal));

        return messageMapper.messageEntityToMessageDto(messageService.save(messageEntity));
    }

    private UserEntity getOwner(final Principal principal) {
        return userService.getByUsername(principal.getName());
    }

}
