package chat.controller;

import chat.mapper.MessageMapper;
import chat.models.dto.MessageDto;
import chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessagesController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @GetMapping(path = "/all")
    public Page<MessageDto> allMessages(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return messageService.all(pageable).map(messageMapper::messageEntityToMessageDto);
    }
}
