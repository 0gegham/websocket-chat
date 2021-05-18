package chat.repositories;

import chat.models.entities.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Iterable<Message> findByOwner(Long id);
}
