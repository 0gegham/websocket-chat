package chat.mapper;

import chat.models.dto.MessageDTO;
import chat.models.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageDtoToMessageEntity(MessageDTO messageDTO);
    MessageDTO messageEntityToMessageDto(Message messageEntity);
}
