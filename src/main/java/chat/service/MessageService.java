package chat.service;

import chat.models.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message save(Message message);
    Iterable<Message> getByOwnerId(Long id);
    Page<Message> all(Pageable pageable);
}
