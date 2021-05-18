package chat.service.impl;

import chat.models.entities.Message;
import chat.repositories.MessageRepository;
import chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Iterable<Message> getByOwnerId(Long id) {
        return messageRepository.findByOwner(id);
    }

    @Override
    public Page<Message> all(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }
}
